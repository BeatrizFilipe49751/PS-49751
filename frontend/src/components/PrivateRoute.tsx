import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../components/AuthContext"; // adjust path if needed

interface PrivateRouteProps {
    children: React.ReactNode;
}

export default function PrivateRoute({ children }: PrivateRouteProps) {
    const { isLoggedIn } = useAuth();

    return isLoggedIn ? <>{children}</> : <Navigate to="/login" replace />;
}