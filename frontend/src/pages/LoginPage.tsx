import { useState } from "react";
import { useNavigate } from "react-router-dom";
import FormLayout from "../components/FormLayout";
import LoadingPage from "./LoadingPage";
import { loginUser } from "../api/userService";
import { useAuth } from "../components/AuthContext";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try {
            const response = await loginUser({ email, password });
            const { token, id } = response.data;

            localStorage.setItem("token", token);
            localStorage.setItem("userId", id);
            login();
            navigate("/");
        } catch (error: any) {
            console.error("Login failed:", error);
            alert("Invalid email or password");
            setLoading(false);
        }
    };

    if (loading) return <LoadingPage />;

    return (
        <FormLayout
            formTitle="Login"
            formFields={
                <>
                    <label>
                        Email:
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Password:
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </label>
                </>
            }
            formButtonText="Login"
            onSubmit={handleLogin}
            side="right"
            sideTitle="Don't have an account?"
            sideText="Register now to import, manage and export your scientific CV easily."
            sideButtonText="Register"
            sideButtonPath="/register"
        />
    );
}