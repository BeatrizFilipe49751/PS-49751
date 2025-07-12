import { Link } from "react-router-dom";
import "./HomePage.css";
import { useAuth } from "../components/AuthContext";

export default function HomePage() {
    const { isLoggedIn } = useAuth();

    const features = [
        {
            iconPath: "/assets/import-icon.png",
            title: "Import your CV",
            description: "Import your CV data from different sources to start managing it centrally.",
            path: "/import",
        },
        {
            iconPath: "/assets/cv-icon.png",
            title: "Check your CV",
            description: "Browse, edit, and review the details of your current scientific CV.",
            path: "/cv?section=Information",
        },
        {
            iconPath: "/assets/cienciavitae-icon.png",
            title: "Export to CIÊNCIAVITAE",
            description: "Export your updated CV directly to the CIÊNCIAVITAE platform.",
            path: "/export",
        },
    ];

    return (
        <div className="homepage-container">

            <div className="homepage-content">
                <h1 className="homepage-title">Features</h1>

                <div className="features-grid">
                    {features.map((feature, index) => (
                        <div className="feature-card" key={index}>
                            <img src={feature.iconPath} alt={feature.title} className="feature-icon" />
                            <h2 className="feature-subtitle">{feature.title}</h2>

                            {isLoggedIn ? (
                                <Link to={feature.path} className="feature-button">
                                    {feature.title}
                                </Link>
                            ) : (
                                <p className="feature-description">
                                    {feature.description}
                                </p>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
