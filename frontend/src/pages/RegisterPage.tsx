import { useState } from "react";
import { useNavigate } from "react-router-dom";
import FormLayout from "../components/FormLayout";
import LoadingPage from "./LoadingPage";
import { registerUser, loginUser } from "../api/userService";
import { useAuth } from "../components/AuthContext";

export default function RegisterPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [cienciaID, setCienciaID] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setLoading(true);

        try {
            // First, register the user
            await registerUser({ email, password, cienciaID });

            // Then, immediately log them in
            const loginResponse = await loginUser({ email, password });
            const { token, id } = loginResponse.data;

            localStorage.setItem("token", token);
            localStorage.setItem("userId", id);
            login();
            navigate("/");
        } catch (error: any) {
            console.error("Registration failed:", error);
            alert("Something went wrong. Please check your input.");
            setLoading(false);
        }
    };

    if (loading) return <LoadingPage />;

    return (
        <FormLayout
            formTitle="Register"
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
                    <label>
                        CiênciaVitae ID:
                        <input
                            type="text"
                            value={cienciaID}
                            onChange={(e) => setCienciaID(e.target.value)}
                            required
                        />
                    </label>
                </>
            }
            formButtonText="Register"
            onSubmit={handleRegister}
            side="left"
            sideTitle="Already have an account?"
            sideText="Log in to manage and export your scientific CV."
            sideButtonText="Login"
            sideButtonPath="/login"
        />
    );
}
