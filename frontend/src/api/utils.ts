export function formatSectionTitle(field: string): string {
    return field
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, str => str.toUpperCase());
}

export function formatFieldName(field: string): string {
    return field
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, str => str.toUpperCase());
}

export const sectionToCvFields: Record<string, string[]> = {
  Information: ['summary', 'identifiers', 'authors'],
  Contacts: ['addresses', 'emails', 'phones', 'websites'],
  Languages: ['languages'],
  Education: ['educations'],
  Experience: ['profExp'],
  Projects: ['projects'],
  Productions: ['productions'],
  Activities: ['activities'],
  Distinctions: ['distinctions'],
};
