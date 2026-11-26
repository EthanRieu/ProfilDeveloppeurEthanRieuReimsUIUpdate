package com.example.profildeveloppeur.model

/**
 * Données affichées par la fiche profil.
 * Toutes les données sont locales (écrites en Kotlin) : les composables les reçoivent
 * en paramètres et ne contiennent aucune donnée en dur.
 */
data class DeveloperProfile(
    val name: String,
    val role: String,
    val description: String,
    val skills: List<Skill>,
    val projects: List<Project>,
    val email: String,
    val location: String,
    val isAvailable: Boolean,
    val availability: String,
    val portfolioUrl: String
)

/** Une compétence, rangée dans une catégorie (utilisée plus tard pour choisir son icône). */
data class Skill(
    val name: String,
    val category: SkillCategory
)

enum class SkillCategory(val label: String) {
    LANGUAGE("Langage"),
    FRAMEWORK("Framework"),
    TOOL("Outil"),
    DATABASE("Base de données")
}

/** Un projet ou une expérience affiché(e) dans une carte. */
data class Project(
    val title: String,
    val type: ProjectType,
    val period: String,
    val description: String,
    val technologies: List<String>
)

enum class ProjectType(val label: String) {
    PROFESSIONAL("Expérience"),
    SCHOOL("Projet école"),
    PERSONAL("Projet perso")
}

// Profil principal (utilisé dans l'application et les previews)
val androidJuniorProfile = DeveloperProfile(
    name = "Ethan Rieu",
    role = "Développeur Android junior",
    description = "Étudiant en Master 1 à l'ESGI Reims, passionné par le développement mobile. " +
        "J'apprends Jetpack Compose et je recherche une alternance en développement Android.",
    skills = listOf(
        Skill("Kotlin", SkillCategory.LANGUAGE),
        Skill("Jetpack Compose", SkillCategory.FRAMEWORK),
        Skill("Material 3", SkillCategory.FRAMEWORK),
        Skill("Git / GitHub", SkillCategory.TOOL),
        Skill("Android Studio", SkillCategory.TOOL),
        Skill("SQL", SkillCategory.DATABASE)
    ),
    projects = listOf(
        Project(
            title = "Profil développeur",
            type = ProjectType.SCHOOL,
            period = "2026",
            description = "Application Jetpack Compose présentant un profil de développeur " +
                "avec un thème Material 3 clair / sombre.",
            technologies = listOf("Kotlin", "Compose", "Material 3")
        ),
        Project(
            title = "Application de gestion de tâches",
            type = ProjectType.PERSONAL,
            period = "2025",
            description = "Petite application Android pour organiser ses tâches quotidiennes " +
                "par listes et priorités.",
            technologies = listOf("Kotlin", "Android", "SQLite")
        ),
        Project(
            title = "Stage développeur web",
            type = ProjectType.PROFESSIONAL,
            period = "Avr. – Juin 2025",
            description = "Développement de fonctionnalités sur un site vitrine et " +
                "maintenance d'une base de données clients.",
            technologies = listOf("PHP", "JavaScript", "MySQL")
        )
    ),
    email = "ethan.rieu@exemple.com",
    location = "Reims, France",
    isAvailable = true,
    availability = "Alternance à partir de septembre 2027",
    portfolioUrl = "github.com/EthanRieu"
)

// Profil fictif pour montrer une deuxième variante (non disponible, autres compétences)
val fullstackProfile = DeveloperProfile(
    name = "Camille Martin",
    role = "Développeuse full-stack",
    description = "Développeuse full-stack avec 4 ans d'expérience sur des applications web et mobiles.",
    skills = listOf(
        Skill("TypeScript", SkillCategory.LANGUAGE),
        Skill("React", SkillCategory.FRAMEWORK),
        Skill("Node.js", SkillCategory.FRAMEWORK),
        Skill("Docker", SkillCategory.TOOL),
        Skill("PostgreSQL", SkillCategory.DATABASE)
    ),
    projects = listOf(
        Project(
            title = "Développeuse full-stack — Agence Web",
            type = ProjectType.PROFESSIONAL,
            period = "2022 – aujourd'hui",
            description = "Conception d'applications web pour des clients PME, " +
                "de l'API jusqu'à l'interface.",
            technologies = listOf("React", "Node.js", "PostgreSQL")
        ),
        Project(
            title = "Plateforme de réservation",
            type = ProjectType.PERSONAL,
            period = "2023",
            description = "Application de réservation de salles avec tableau de bord " +
                "d'administration.",
            technologies = listOf("TypeScript", "Docker")
        )
    ),
    email = "camille.martin@exemple.com",
    location = "Paris, France",
    isAvailable = false,
    availability = "En poste, non disponible actuellement",
    portfolioUrl = "camille-martin.dev"
)
