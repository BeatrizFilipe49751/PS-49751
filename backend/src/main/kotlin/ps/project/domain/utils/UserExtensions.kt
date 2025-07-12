package ps.project.domain.utils

import ps.project.domain.User

fun User.hasCvData(): Boolean =
    educations.isNotEmpty()     ||
    projects.isNotEmpty()       ||
    productions.isNotEmpty()    ||
    profExp.isNotEmpty()        ||
    activities.isNotEmpty()     ||
    languages.isNotEmpty()      ||
    distinctions.isNotEmpty()   ||
    addresses.isNotEmpty()      ||
    emails.isNotEmpty()         ||
    phones.isNotEmpty()         ||
    websites.isNotEmpty()       ||
    identifiers.isNotEmpty()