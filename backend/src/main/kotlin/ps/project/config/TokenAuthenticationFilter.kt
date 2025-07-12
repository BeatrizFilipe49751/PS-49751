package ps.project.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ps.project.repository.UserTokenRepository
import java.io.IOException
import java.time.LocalDateTime

@Component
class TokenAuthenticationFilter(private val userTokenRepository: UserTokenRepository) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = getTokenFromRequest(request)
        if (token != null) {
            val userToken = userTokenRepository.findByToken(token).orElse(null)
            if (userToken != null && !userToken.expiresAt.isBefore(LocalDateTime.now())) {
                // Token is valid, so set the user authentication in the context
                val authentication = UsernamePasswordAuthenticationToken(userToken.user, null, listOf())
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                // Token invalid or expired
                SecurityContextHolder.clearContext()
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}