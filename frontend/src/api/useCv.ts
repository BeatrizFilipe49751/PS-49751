import { useEffect, useState } from 'react';
import { getCv, putCv } from '../api/cvService';
import { CvDTO } from "../interfaces/models/CvDTO";

export function useCv() {
    const [cv, setCv] = useState<CvDTO | null>(null);
    const [originalCv, setOriginalCv] = useState<CvDTO | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [saving, setSaving] = useState(false);
    const [editMode, setEditMode] = useState(false);

    useEffect(() => {
        getCv()
            .then(response => setCv(response))
            .catch(e => setError(e.message));
    }, []);

    const enterEditMode = () => {
        if (cv) {
            setOriginalCv(JSON.parse(JSON.stringify(cv)));
            setEditMode(true);
        }
    };

    const cancelEdit = () => {
        if (originalCv) {
            setCv(originalCv);
            setOriginalCv(null);
        }
        setEditMode(false);
    };

    const handleSave = async () => {
        if (!cv) return;
        setSaving(true);
        try {
            await putCv(cv);
            alert('CV updated successfully');
            setOriginalCv(null);
        } catch (e: any) {
            alert('Error saving CV: ' + e.message);
        } finally {
            setSaving(false);
            setEditMode(false);
        }
    };

    return {
        cv,
        setCv,
        error,
        saving,
        editMode,
        enterEditMode,
        cancelEdit,
        handleSave
    };
}
