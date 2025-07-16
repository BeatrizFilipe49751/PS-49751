// components/CvSectionsRenderer.tsx
import { CvDTO } from "../interfaces/models/CvDTO";
import { formatFieldName, formatSectionTitle, sectionToCvFields } from '../api/utils';
import { RenderDtoFields } from './RenderDtoFields';
import CvSection from './CvSection';
import "../pages/CvPage.css";

export function CvSectionsRenderer({
    cv,
    setCv,
    selectedSection,
    editMode
}: {
    cv: CvDTO;
    setCv: React.Dispatch<React.SetStateAction<CvDTO | null>>;
    selectedSection: string;
    editMode: boolean;
}) {
    if (!selectedSection || !sectionToCvFields[selectedSection]) {
        return <div>Please select a valid section from the sidebar.</div>;
    }

    const fieldKeys = sectionToCvFields[selectedSection];

    const updateField = (fieldKey: string, index: number, updatedItem: any) => {
        const section = cv[fieldKey as keyof CvDTO];
        if (!Array.isArray(section)) return;

        const updatedSection = [...section];
        updatedSection[index] = updatedItem;

        setCv(prev => ({
            ...prev!,
            [fieldKey]: updatedSection
        }));
    };

    return (
        <>
            {fieldKeys.map(fieldKey => {
                const sectionData = cv[fieldKey as keyof CvDTO];
                if (sectionData == null || (Array.isArray(sectionData) && sectionData.length === 0)) {
                    return null;
                }

                return (
                    <CvSection key={fieldKey} id={fieldKey} title={formatSectionTitle(fieldKey)}>
                        {Array.isArray(sectionData) ? (
                            sectionData.map((item: Record<string, any>, index: number) => (
                                <div className="cv-entry" key={item.id ?? index}>
                                    <RenderDtoFields
                                        data={item}
                                        parentSection={fieldKey}
                                        onChange={updated => updateField(fieldKey, index, updated)}
                                        editMode={editMode}
                                    />
                                </div>
                            ))
                        ) : typeof sectionData === 'object' && sectionData !== null ? (
                            <RenderDtoFields
                                data={sectionData as Record<string, any>}
                                parentSection={fieldKey}
                                onChange={updated =>
                                    setCv(prev => ({
                                        ...prev!,
                                        [fieldKey]: updated,
                                    }))
                                }
                                editMode={editMode}
                            />
                        ) : (
                            <div className="cv-field">
                                <strong>{formatFieldName(fieldKey)}:</strong>{' '}
                                {editMode ? (
                                    typeof sectionData === 'string' && sectionData.length > 60 ? (
                                        <textarea
                                            value={sectionData}
                                            onChange={e =>
                                                setCv(prev => ({
                                                    ...prev!,
                                                    [fieldKey]: e.target.value,
                                                }))
                                            }
                                            rows={4}
                                            style={{ width: '100%' }}
                                        />
                                    ) : (
                                        <input
                                            type="text"
                                            value={sectionData as string | number}
                                            onChange={e =>
                                                setCv(prev => ({
                                                    ...prev!,
                                                    [fieldKey]:
                                                        typeof sectionData === 'number'
                                                            ? Number(e.target.value)
                                                            : e.target.value,
                                                }))
                                            }
                                            style={{ width: '100%' }}
                                        />
                                    )
                                ) : (
                                    <p>{sectionData}</p>
                                )}
                            </div>
                        )}
                    </CvSection>
                );
            })}
        </>
    );
}
