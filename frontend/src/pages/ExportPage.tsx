import React, { useEffect, useState } from "react";
import { sendCvToCienciaVitae } from "../api/cvService";
import LoadingPage from "./LoadingPage";

export default function ExportPage() {
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const exportCv = async () => {
            try {
                const response = await sendCvToCienciaVitae();
                setMessage(response.data || "CV successfully sent to CiênciaVitae.");
            } catch (err: any) {
                setError(err.response?.data || "Error sending the CV.");
            } finally {
                setLoading(false);
            }
        };

        exportCv();
    }, []);

    if (loading) return <LoadingPage />;

    return (
        <div style={{ padding: 20 }}>
            <h2>Export CV to CiênciaVitae</h2>
            {message && <p style={{ color: "green" }}>{message}</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
}