import {ActivityDTOTypeEnum, ActivityDTORoleEnum, ActivityDTODegreeEnum} from '../interfaces/models/ActivityDTO';
import {AddressDTOTypeEnum} from '../interfaces/models/AddressDTO';
import {EmailDTOTypeEnum} from '../interfaces/models/EmailDTO';
import {PhoneDTOTypeEnum, PhoneDTODeviceEnum} from '../interfaces/models/PhoneDTO';
import {WebsiteDTOTypeEnum} from '../interfaces/models/WebsiteDTO';
import {DistinctionDTOTypeEnum} from '../interfaces/models/DistinctionDTO';
import {EducationDTODegreeEnum, EducationDTOStatusEnum} from '../interfaces/models/EducationDTO';
import {IdentifierDTOTypeEnum} from '../interfaces/models/IdentifierDTO';
import {LanguageDTOComprehensionEnum} from '../interfaces/models/LanguageDTO';
import {ProductionDTOTypeEnum} from '../interfaces/models/ProductionDTO';
import {ProfessionalExperienceDTOTypeEnum} from '../interfaces/models/ProfessionalExperienceDTO';
import {ProjectDTOFundingTypeEnum, ProjectDTOStateEnum} from '../interfaces/models/ProjectDTO';
import {SupervisorDTORoleEnum} from '../interfaces/models/SupervisorDTO';

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

export const enumFieldMap: Record<string, Record<string, string>> = {
  'activities.type': ActivityDTOTypeEnum,
  'activities.role': ActivityDTORoleEnum,
  'activities.degree': ActivityDTODegreeEnum,
  'addresses.type': AddressDTOTypeEnum,
  'emails.type': EmailDTOTypeEnum,
  'phones.type': PhoneDTOTypeEnum,
  'phones.device': PhoneDTODeviceEnum,
  'websites.type': WebsiteDTOTypeEnum,
  'distinctions.type': DistinctionDTOTypeEnum,
  'educations.degree': EducationDTODegreeEnum,
  'educations.status': EducationDTOStatusEnum,
  'identifiers.type': IdentifierDTOTypeEnum,
  'languages.comprehension': LanguageDTOComprehensionEnum,
  'languages.reading': LanguageDTOComprehensionEnum,
  'languages.speaking': LanguageDTOComprehensionEnum,
  'languages.writing': LanguageDTOComprehensionEnum,
  'productions.type': ProductionDTOTypeEnum,
  'profExp.type': ProfessionalExperienceDTOTypeEnum,
  'projects.fundingType': ProjectDTOFundingTypeEnum,
  'projects.state': ProjectDTOStateEnum,
  'educations.thesis.supervisors.role': SupervisorDTORoleEnum,
};
