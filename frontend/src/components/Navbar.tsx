import { Link, useNavigate } from 'react-router-dom';
import { logoutUser } from "../api/userService";
import './Navbar.css';
import { useAuth } from "./AuthContext";

const Navbar: React.FC = () => {
    const { isLoggedIn, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            await logoutUser();
        } catch (error) {
            console.error("Logout API call failed:", error);
        }
        // Clear local data regardless
        localStorage.removeItem("token");
        localStorage.removeItem("userId");
        logout();
        navigate("/");
    };

    return (
        <nav className="navbar">
            <div className="navbar-logo">
                <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
                    Sistema de Interface de Dados para a CIÊNCIAVITAE
                </Link>
            </div>
            <ul className="navbar-links">
                {isLoggedIn ? (
                    <>
                        <li><button onClick={handleLogout} className="login-button">Logout</button></li>
                    </>
                ) : (
                    <>
                        <li><Link to="/login" className="login-button">Login</Link></li>
                    </>
                )}
            </ul>
        </nav>
    );

};

export default Navbar;
