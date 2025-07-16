import { useLocation } from 'react-router-dom';
import { useCv } from '../api/useCv';
import { CvSectionsRenderer } from '../components/CvSectionsRenderer';
import Sidebar from '../components/Sidebar';
import LoadingPage from './LoadingPage';

export default function CvPage() {
    const {
        cv,
        setCv,
        error,
        saving,
        editMode,
        enterEditMode,
        cancelEdit,
        handleSave
    } = useCv();

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const selectedSection = queryParams.get('section');

    if (error) return <div>Error: {error}</div>;
    if (!cv) return <LoadingPage />;

    return (
        <div>
            <Sidebar />
            <main className="cv-main-container">
                <div className="cv-content-wrapper">
                    <CvSectionsRenderer
                        cv={cv}
                        setCv={setCv}
                        selectedSection={selectedSection!}
                        editMode={editMode}
                    />
                </div>
                <div className="edit-action-buttons">
                    {!editMode ? (
                        <button className="edit-toggle-button" onClick={enterEditMode} title="Edit CV">✏️</button>
                    ) : (
                        <>
                            <button className="save-button" onClick={handleSave} disabled={saving} title="Save Changes">✅</button>
                            <button className="cancel-button" onClick={cancelEdit} title="Cancel Edit">❌</button>
                        </>
                    )}
                </div>
            </main>
        </div>
    );
}
