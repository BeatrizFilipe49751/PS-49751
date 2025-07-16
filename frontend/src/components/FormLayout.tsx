import "./FormLayout.css";
import { ReactNode } from "react";
import { useNavigate } from "react-router-dom";

interface FormLayoutProps {
    formTitle: string;
    formFields: ReactNode;
    formButtonText: string;
    onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;

    side: "left" | "right";
    sideTitle: string;
    sideText: string;
    sideButtonText: string;
    sideButtonPath: string;
}

export default function FormLayout({
    formTitle,
    formFields,
    formButtonText,
    onSubmit,
    side,
    sideTitle,
    sideText,
    sideButtonText,
    sideButtonPath
}: FormLayoutProps) {
    const navigate = useNavigate();

    return (
        <div className="form-page">
            <div className={`form-box ${side === "left" ? "reverse" : ""}`}>
                <div className="form-side form-main">
                    <h2>{formTitle}</h2>
                    <form
                        onSubmit={onSubmit}
                        className="form-content"
                    >
                    {formFields}
                        <button type="submit" className="submit-button">
                            {formButtonText}
                        </button>
                    </form>
                </div>
                <div className="form-side form-secondary">
                    <h3>{sideTitle}</h3>
                    <p>{sideText}</p>
                    <button onClick={() => navigate(sideButtonPath)} className="side-button">
                        {sideButtonText}
                    </button>
                </div>
            </div>
        </div>
    );
}
