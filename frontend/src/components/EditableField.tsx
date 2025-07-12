import React, { useState } from 'react';

interface EditableFieldProps {
    label: string;
    value: string;
    onSave: (newValue: string) => void;
}

export default function EditableField({ label, value, onSave }: EditableFieldProps) {
    const [editing, setEditing] = useState(false);
    const [newValue, setNewValue] = useState(value);

    return (
        <div>
            <label>{label}: </label>
            {editing ? (
                <>
                    <input value={newValue} onChange={e => setNewValue(e.target.value)} />
                    <button onClick={() => { onSave(newValue); setEditing(false); }}>Salvar</button>
                </>
            ) : (
                <>
                    <span>{value}</span>
                    <button onClick={() => setEditing(true)}>Editar</button>
                </>
            )}
        </div>
    );
}
