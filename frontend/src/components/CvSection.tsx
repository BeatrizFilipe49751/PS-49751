import React from 'react';
import './CvSection.css'; // <- Make sure this import exists or styles are global

interface CvSectionProps {
    id: string;
    title: string;
    children: React.ReactNode;
}

export default function CvSection({ id, title, children }: CvSectionProps) {
    return (
        <section id={id} className="cv-section">
            <h2 className="cv-section-title">{title}</h2>
            <div className="cv-section-content">{children}</div>
        </section>
    );
}
