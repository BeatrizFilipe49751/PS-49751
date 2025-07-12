import React from 'react';
import { Link } from 'react-router-dom';
import { sectionToCvFields } from '../api/utils';
import './Sidebar.css';

export default function Sidebar() {
    const sections = Object.keys(sectionToCvFields);

    return (
        <nav className="sidebar">
            <ul>
                {sections.map(section => (
                    <li key={section}>
                        <Link to={`/cv?section=${encodeURIComponent(section)}`}>
                            {section}
                        </Link>
                    </li>
                ))}
            </ul>
        </nav>
    );
}