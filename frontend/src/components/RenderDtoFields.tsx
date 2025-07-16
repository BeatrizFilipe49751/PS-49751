import React from 'react';
import { formatFieldName, enumFieldMap } from '../api/utils';

export function RenderDtoFields({
    data,
    parentSection,
    onChange,
    editMode,
    depth = 0,
}: {
    data: Record<string, any>;
    parentSection?: string;
    onChange: (updated: Record<string, any>) => void;
    editMode: boolean;
    depth?: number;
}) {
    const handleChange = (key: string, value: any) => {
        const updated = { ...data, [key]: value };
        onChange(updated);
    };

    const entries = Object.entries(data).filter(([key, value]) => {
        if (key === 'id' && parentSection !== 'identifiers') return false;
        if (value == null) return false;
        if (Array.isArray(value) && value.length === 0) return false;
        return true;
    });

    if (
        entries.length === 1 &&
        (typeof entries[0][1] === 'string' || typeof entries[0][1] === 'number')
    ) {
        const [onlyKey, onlyValue] = entries[0];
        return (
            <div className="cv-field" style={{ marginLeft: depth * 20 }}>
                {editMode ? (
                    <input
                        type="text"
                        value={onlyValue}
                        onChange={e => handleChange(onlyKey, e.target.value)}
                    />
                ) : (
                    <span>{onlyValue}</span>
                )}
            </div>
        );
    }

    return (
        <>
            {entries.map(([key, value]) => {
                const fullKey = `${parentSection}.${key}`;
                const enumObj = enumFieldMap[fullKey];
                const enumOptions = enumObj ? Object.values(enumObj) : undefined;
                const indentStyle = { marginLeft: depth * 20 };

                if (Array.isArray(value)) {
                    return (
                        <RenderArrayField
                            keyName={key}
                            value={value}
                            fullKey={fullKey}
                            handleChange={handleChange}
                            editMode={editMode}
                            depth={depth}
                        />
                    );
                }

                if (typeof value === 'object') {
                    return (
                        <div key={key} className="cv-field-object" style={indentStyle}>
                            <strong>• {formatFieldName(key)}:</strong>
                            <RenderDtoFields
                                data={value}
                                parentSection={fullKey}
                                onChange={upd => {
                                    handleChange(key, upd);
                                }}
                                editMode={editMode}
                                depth={depth + 1}
                            />
                        </div>
                    );
                }

                return (
                    <div key={key} className="cv-field" style={indentStyle}>
                        <strong>{formatFieldName(key)}:</strong>{' '}
                        <RenderField
                            keyName={key}
                            value={value}
                            enumOptions={enumOptions}
                            editMode={editMode}
                            handleChange={handleChange}
                        />
                    </div>
                );
            })}
        </>
    );
}

function RenderArrayField({
    keyName,
    value,
    fullKey,
    handleChange,
    editMode,
    depth,
}: {
    keyName: string;
    value: any[];
    fullKey: string;
    handleChange: (key: string, value: any) => void;
    editMode: boolean;
    depth: number;
}) {
    if (value.length === 0) return null;

    return (
        <div className="cv-field-array" style={{ marginLeft: depth * 20 }}>
            <strong>• {formatFieldName(keyName)}:</strong>
            {value.map((item, idx) =>
                typeof item === "object" ? (
                    <div key={idx} style={{ marginLeft: 20 }}>
                        <RenderDtoFields
                            data={item}
                            parentSection={fullKey}
                            onChange={(upd) => {
                                const newArr = [...value];
                                newArr[idx] = upd;
                                handleChange(keyName, newArr);
                            }}
                            editMode={editMode}
                            depth={depth + 2}
                        />
                    </div>
                ) : (
                    <div key={idx} style={{ marginLeft: 20 }}>
                        {item}
                    </div>
                )
            )}
        </div>
    );
}

function RenderField({
    keyName,
    value,
    enumOptions,
    editMode,
    handleChange,
}: {
    keyName: string;
    value: any;
    enumOptions?: string[];
    editMode: boolean;
    handleChange: (key: string, value: any) => void;
}) {
    const format = formatFieldName;

    if (editMode) {
        if (enumOptions) {
            return (
                <select value={value} onChange={e => handleChange(keyName, e.target.value)}>
                    {enumOptions.map(opt => (
                        <option key={opt} value={opt}>
                            {format(opt)}
                        </option>
                    ))}
                </select>
            );
        }

        if (typeof value === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(value)) {
            return <input type="date" value={value} onChange={e => handleChange(keyName, e.target.value)} />;
        }

        if (typeof value === 'string' || typeof value === 'number') {
            return (
                <input
                    type={typeof value === 'number' ? 'number' : 'text'}
                    value={value}
                    onChange={e =>
                        handleChange(keyName, typeof value === 'number' ? Number(e.target.value) : e.target.value)
                    }
                />
            );
        }

        if (typeof value === 'boolean') {
            return (
                <input
                    type="checkbox"
                    checked={value}
                    onChange={e => handleChange(keyName, e.target.checked)}
                />
            );
        }

        return <span>{String(value)}</span>;
    }

    return <span>{String(value)}</span>;
}