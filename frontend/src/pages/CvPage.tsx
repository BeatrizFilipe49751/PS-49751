import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { getCv } from "../api/cvService";
import CvSection from '../components/CvSection';
import Sidebar from "../components/Sidebar";
import { CvDTO } from "../interfaces/models/CvDTO";
import { formatSectionTitle, formatFieldName, sectionToCvFields } from "../api/utils";
import LoadingPage from "./LoadingPage";
import "./CvPage.css"; // Import the CSS file

export default function CvPage() {
    const [cv, setCv] = useState<CvDTO | null>(null);

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const selectedSection = queryParams.get('section');

    useEffect(() => {
        getCv()
            .then(response => setCv(response.data))
            .catch(error => console.error("Failed to fetch CV:", error));
    }, []);

    if (!cv) return <LoadingPage />;

    if (!selectedSection || !sectionToCvFields[selectedSection]) {
        return <div>Please select a valid section from the sidebar.</div>;
    }

    const fieldsToShow = sectionToCvFields[selectedSection];

    return (
        <div>
            <Sidebar />
            <main className="cv-main-container">
                <div className="cv-content-wrapper">
                    {fieldsToShow.map(fieldKey => {
                        const sectionData = (cv as any)[fieldKey];

                        if (typeof sectionData === 'string') {
                            return (
                                <CvSection key={fieldKey} id={fieldKey} title={formatSectionTitle(fieldKey)}>
                                    <p>{sectionData}</p>
                                </CvSection>
                            );
                        }

                        if (Array.isArray(sectionData) && sectionData.length > 0) {
                            return (
                                <CvSection key={fieldKey} id={fieldKey} title={formatSectionTitle(fieldKey)}>
                                    {sectionData.map((item: Record<string, any>, index: number) => (
                                        <div className="cv-entry" key={item.id ?? index}>
                                            <RenderDtoFields data={item} parentSection={fieldKey} />
                                        </div>
                                    ))}
                                </CvSection>
                            );
                        }

                        return null;
                    })}
                </div>
            </main>
        </div>
    );
}

function RenderDtoFields({
                             data,
                             parentSection,
                         }: {
    data: Record<string, any>;
    parentSection?: string;
}) {
    const entries = Object.entries(data).filter(([key, value]) => {
        if (key === 'id' && parentSection !== 'identifiers') return false;
        if (value == null) return false;
        if (Array.isArray(value) && value.length === 0) return false;
        return true;
    });

    // If only one field, render inline without label or with simpler formatting
    if (entries.length === 1) {
        const [key, value] = entries[0];
        return <div>{renderValue(value)}</div>;
    }

    // Otherwise render as before
    return (
        <>
            {entries.map(([key, value]) => (
                <div key={key}>
                    <strong>{formatFieldName(key)}:</strong> {renderValue(value)}
                </div>
            ))}
        </>
    );
}

function renderValue(value: any): React.ReactNode {
    if (typeof value === 'string' || typeof value === 'number' || typeof value === 'boolean') {
        return value.toString();
    }

    if (Array.isArray(value)) {
        return (
            <ul>
                {value.map((item, index) => (
                    <li key={index}>{renderValue(item)}</li>
                ))}
            </ul>
        );
    }

    if (typeof value === 'object') {
        return (
            <div className="cv-subfields">
                {Object.entries(value)
                    .filter(([k, v]) =>
                        k !== 'id' && v != null && !(Array.isArray(v) && v.length === 0)
                    )
                    .map(([k, v]) => (
                        <p key={k}>
                            <strong>{formatFieldName(k)}:</strong> {renderValue(v)}
                        </p>
                    ))}
            </div>
        );
    }

    return String(value);
}
