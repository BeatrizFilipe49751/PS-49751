import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoadingPage from "./LoadingPage";
import { importCv } from "../api/cvService";
import FormLayout from "../components/FormLayout";

export default function ImportPage() {
    const [source, setSource] = useState("");
    const [file, setFile] = useState<File | null>(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleImport = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!file) {
            alert("Please select a file.");
            return;
        }

        setLoading(true);
        try {
            await importCv(source, file);
            navigate("/cv?section=Information");
        } catch (error) {
            console.error("CV import failed:", error);
            alert("Failed to import CV. Please try again.");
            setLoading(false);
        }
    };

    if (loading) return <LoadingPage />;

    return (
        <FormLayout
            formTitle="Import CV"
            formFields={
                <>
                    <label>
                        Source:
                        <input
                            type="text"
                            value={source}
                            onChange={(e) => setSource(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        CV File:
                        <input
                            type="file"
                            accept=".xml,.json"
                            onChange={(e) => {
                                if (e.target.files?.[0]) {
                                    setFile(e.target.files[0]);
                                }
                            }}
                            required
                        />
                    </label>
                </>
            }
            formButtonText="Import"
            onSubmit={handleImport}
            side="right"
            sideTitle="Want to check your CV?"
            sideText="Once you've imported your file, you can browse and edit your scientific CV."
            sideButtonText="View CV"
            sideButtonPath="/cv?section=Information"
        />
    );
}
