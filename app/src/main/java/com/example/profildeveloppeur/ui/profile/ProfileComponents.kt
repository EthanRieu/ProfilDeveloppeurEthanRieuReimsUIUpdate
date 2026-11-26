package com.example.profildeveloppeur.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.example.profildeveloppeur.R
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

/** Icône associée à chaque catégorie de compétence. */
private fun SkillCategory.icon(): ImageVector = when (this) {
    SkillCategory.LANGUAGE -> Icons.Filled.Create
    SkillCategory.FRAMEWORK -> Icons.Filled.Build
    SkillCategory.TOOL -> Icons.Filled.Settings
    SkillCategory.DATABASE -> Icons.AutoMirrored.Filled.List
}

/** Information secondaire sous la forme « libellé : valeur » (contact, localisation…). */
@Composable
fun InfoLine(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$label : $value",
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
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
