package com.example.profildeveloppeur.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.example.profildeveloppeur.R
import com.example.profildeveloppeur.model.Project
import com.example.profildeveloppeur.model.ProjectType
import com.example.profildeveloppeur.model.Skill
import com.example.profildeveloppeur.model.SkillCategory
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * En-tête de la fiche : avatar (avec badge de disponibilité superposé), nom et rôle,
 * posés sur une Surface colorée cohérente avec le thème.
 */
@Composable
fun ProfileHeader(
    name: String,
    role: String,
    location: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        // Row : avatar à gauche, textes à droite, centrés verticalement
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarWithBadge(isAvailable = isAvailable)

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = role,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

/**
 * Avatar circulaire avec un badge de disponibilité superposé en bas à droite.
 * La Box permet d'empiler l'Image et le badge : le badge est placé avec Modifier.align.
 */
@Composable
fun AvatarWithBadge(
    isAvailable: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 88.dp
) {
    val badgeColor = if (isAvailable) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.outline
    }
    val badgeDescription = if (isAvailable) "Disponible" else "Non disponible"

    Box(modifier = modifier.size(size)) {
        Image(
            painter = painterResource(id = R.drawable.avatar_placeholder),
            contentDescription = "Avatar du développeur",
            // La silhouette prend la couleur primaire du thème (clair ou sombre)
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .padding(size / 8)
        )

        // Badge superposé : pastille ronde entourée de la couleur du fond de l'en-tête
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(size / 4)
                .clip(CircleShape)
                .background(badgeColor)
                .border(3.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .semantics { contentDescription = badgeDescription }
        )
    }
}

/** Titre d'une section de la fiche (ex : « Compétences »). */
@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

/**
 * Compétence affichée sous forme de pastille : icône (selon la catégorie) + nom.
 * Composable réutilisable, utilisé pour chaque élément de la LazyRow des compétences.
 */
@Composable
fun SkillChip(
    skill: Skill,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        // Padding INTERNE : espace entre le bord de la pastille et son contenu
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = skill.category.icon(),
                // La catégorie est lue par les lecteurs d'écran avant le nom de la compétence
                contentDescription = skill.category.label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = skill.name,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

/**
 * Carte réutilisable présentant un projet ou une expérience :
 * titre + type, période, description et technologies utilisées.
 */
@Composable
fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row : titre à gauche (prend la place restante), type à droite
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                ProjectTypeLabel(type = project.type)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Période",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = project.period,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Row : technologies alignées sur une même ligne
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                project.technologies.forEach { technology ->
                    TechnologyTag(name = technology)
                }
            }
        }
    }
}

/** Petite étiquette indiquant le type de projet, avec une couleur par type. */
@Composable
fun ProjectTypeLabel(
    type: ProjectType,
    modifier: Modifier = Modifier
) {
    val containerColor = when (type) {
        ProjectType.PROFESSIONAL -> MaterialTheme.colorScheme.primaryContainer
        ProjectType.SCHOOL -> MaterialTheme.colorScheme.secondaryContainer
        ProjectType.PERSONAL -> MaterialTheme.colorScheme.tertiaryContainer
    }
    val contentColor = when (type) {
        ProjectType.PROFESSIONAL -> MaterialTheme.colorScheme.onPrimaryContainer
        ProjectType.SCHOOL -> MaterialTheme.colorScheme.onSecondaryContainer
        ProjectType.PERSONAL -> MaterialTheme.colorScheme.onTertiaryContainer
    }

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = type.label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

/** Technologie utilisée dans un projet, affichée sous forme de tag discret. */
@Composable
fun TechnologyTag(
    name: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

/** Icône associée à chaque catégorie de compétence. */
private fun SkillCategory.icon(): ImageVector = when (this) {
    SkillCategory.LANGUAGE -> Icons.Filled.Create
    SkillCategory.FRAMEWORK -> Icons.Filled.Build
    SkillCategory.TOOL -> Icons.Filled.Settings
    SkillCategory.DATABASE -> Icons.AutoMirrored.Filled.List
}

/**
 * Zone de contact : regroupe les informations structurées du profil
 * dans une Surface cohérente avec le thème.
 */
@Composable
fun ContactSection(
    email: String,
    location: String,
    availability: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle(title = "Contact")
            ContactField(
                label = "Email",
                value = email,
                icon = Icons.Filled.Email
            )
            ContactField(
                label = "Localisation",
                value = location,
                icon = Icons.Filled.LocationOn
            )
            ContactField(
                label = "Disponibilité",
                value = availability,
                icon = if (isAvailable) Icons.Filled.CheckCircle else Icons.Filled.Info
            )
        }
    }
}

/**
 * Information structurée affichée dans un OutlinedTextField en LECTURE SEULE :
 * le champ garde l'apparence Material (libellé, bordure, icône) mais n'est pas modifiable.
 */
@Composable
fun ContactField(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        // Lecture seule : aucune modification possible, le callback ne fait rien
        onValueChange = {},
        readOnly = true,
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                // Le libellé du champ décrit déjà l'information
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        shape = MaterialTheme.shapes.medium,
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier.fillMaxWidth()
    )
}

/**
 * Zone cliquable PERSONNALISÉE indiquant la disponibilité.
 * Le composant signale seulement le clic via [onClick] : c'est le parent qui décide quoi faire.
 */
@Composable
fun AvailabilityBadge(
    isAvailable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = if (isAvailable) {
        "Disponible pour une alternance"
    } else {
        "Non disponible actuellement"
    }

    Surface(
        // Chaîne de modifiers : on part du modifier du parent, puis
        // largeur -> clic -> sémantique pour les lecteurs d'écran
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = "$label. Appuyer pour en savoir plus."
            },
        color = if (isAvailable) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.errorContainer
        },
        shape = MaterialTheme.shapes.medium
    ) {
        // Padding INTERNE : espace autour du texte à l'intérieur du badge
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(12.dp)
        )
    }
}

/** Bouton d'action principal. Le clic est remonté au parent via [onClick]. */
@Composable
fun ContactButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}
