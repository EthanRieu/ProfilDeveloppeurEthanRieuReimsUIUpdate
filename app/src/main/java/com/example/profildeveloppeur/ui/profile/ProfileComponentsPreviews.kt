package com.example.profildeveloppeur.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeur.model.androidJuniorProfile
import com.example.profildeveloppeur.model.fullstackProfile
import com.example.profildeveloppeur.ui.theme.ProfilDeveloppeurTheme

/*
 * Previews des composables réutilisables, isolés de l'écran.
 * @PreviewLightDark génère automatiquement une version claire ET une version sombre.
 * Chaque preview est entourée d'une Surface pour utiliser la couleur de fond du thème.
 */

/** Conteneur commun : applique le thème et un fond cohérent autour du composant. */
@Composable
private fun ComponentPreviewContainer(content: @Composable () -> Unit) {
    ProfilDeveloppeurTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ProfileHeaderPreview() {
    ComponentPreviewContainer {
        ProfileHeader(
            name = androidJuniorProfile.name,
            role = androidJuniorProfile.role,
            location = androidJuniorProfile.location,
            isAvailable = androidJuniorProfile.isAvailable
        )
    }
}

@PreviewLightDark
@Composable
fun AvatarWithBadgePreview() {
    ComponentPreviewContainer {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AvatarWithBadge(isAvailable = true)
            AvatarWithBadge(isAvailable = false)
        }
    }
}

@PreviewLightDark
@Composable
fun SkillChipPreview() {
    ComponentPreviewContainer {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Une pastille par catégorie pour vérifier chaque icône
            androidJuniorProfile.skills
                .distinctBy { it.category }
                .forEach { skill -> SkillChip(skill = skill) }
        }
    }
}

@PreviewLightDark
@Composable
fun ProjectCardPreview() {
    ComponentPreviewContainer {
        ProjectCard(project = androidJuniorProfile.projects.first())
    }
}

@PreviewLightDark
@Composable
fun ProjectCardExperiencePreview() {
    ComponentPreviewContainer {
        ProjectCard(project = fullstackProfile.projects.first())
    }
}

@PreviewLightDark
@Composable
fun ContactFieldPreview() {
    ComponentPreviewContainer {
        ContactField(
            label = "Email",
            value = androidJuniorProfile.email,
            icon = Icons.Filled.Email
        )
    }
}

@PreviewLightDark
@Composable
fun ProfileActionsPreview() {
    ComponentPreviewContainer {
        ProfileActions(onContactClick = {}, onPortfolioClick = {})
    }
}
