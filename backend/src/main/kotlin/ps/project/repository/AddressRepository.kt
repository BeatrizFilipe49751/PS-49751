package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.contact.Address

@Repository
interface AddressRepository : JpaRepository<Address, Int>