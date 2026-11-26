package com.example.profildeveloppeur.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeur.model.DeveloperProfile
import com.example.profildeveloppeur.model.androidJuniorProfile
import com.example.profildeveloppeur.model.fullstackProfile
import com.example.profildeveloppeur.ui.theme.ProfilDeveloppeurTheme

private val ScreenHorizontalPadding = 16.dp

/**
 * Écran principal : un Scaffold qui contient une LazyColumn.
 * Chaque section de la fiche est un `item` de la liste : l'écran défile verticalement
 * et seuls les éléments visibles sont composés.
 */
@Composable
fun ProfileScreen(
    profile: DeveloperProfile,
    onContactClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Marge horizontale commune à toutes les sections de la fiche
    val sectionModifier = Modifier.padding(horizontal = ScreenHorizontalPadding)

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            // innerPadding évite les barres système. La marge horizontale est appliquée
            // par section (sectionModifier) pour laisser la LazyRow défiler jusqu'aux bords.
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 24.dp),
            // Espace identique entre toutes les sections
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item(key = "header") {
                ProfileHeader(
                    name = profile.name,
                    role = profile.role,
                    location = profile.location,
                    isAvailable = profile.isAvailable,
                    modifier = sectionModifier
                )
            }

            item(key = "description") {
                Text(
                    text = profile.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = sectionModifier
                )
            }

            item(key = "skills") {
                Column {
                    SectionTitle(title = "Compétences", modifier = sectionModifier)
                    Spacer(modifier = Modifier.height(8.dp))
                    // LazyRow : liste horizontale, chaque compétence est un SkillChip réutilisable.
                    // Elle occupe toute la largeur ; le contentPadding aligne la 1re pastille
                    // sur les autres sections tout en laissant défiler sous les bords.
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = ScreenHorizontalPadding),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = profile.skills, key = { it.name }) { skill ->
                            SkillChip(skill = skill)
                        }
                    }
                }
            }

            item(key = "projects-title") {
                SectionTitle(title = "Projets & expériences", modifier = sectionModifier)
            }
            // Un item de la LazyColumn par projet, affiché avec le composable réutilisable ProjectCard
            items(items = profile.projects, key = { it.title }) { project ->
                ProjectCard(project = project, modifier = sectionModifier)
            }

            item(key = "contact") {
                Column(modifier = sectionModifier) {
                    SectionTitle(title = "Informations")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoLine(label = "Contact", value = profile.email)
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoLine(label = "Localisation", value = profile.location)
                }
            }

            item(key = "actions") {
                Column(modifier = sectionModifier) {
                    AvailabilityBadge(
                        isAvailable = profile.isAvailable,
                        onClick = onAvailabilityClick
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ContactButton(
                        text = "Contacter ${profile.name}",
                        onClick = onContactClick
                    )
                }
            }
        }
    }
}

@Preview(name = "Profil Android junior (disponible)", showBackground = true)
@Composable
fun ProfileScreenAndroidJuniorPreview() {
    ProfilDeveloppeurTheme {
        ProfileScreen(
            profile = androidJuniorProfile,
            onContactClick = {},
            onAvailabilityClick = {}
        )
    }
}

@Preview(name = "Profil full-stack (non disponible)", showBackground = true)
@Composable
fun ProfileScreenFullstackPreview() {
    ProfilDeveloppeurTheme {
        ProfileScreen(
            profile = fullstackProfile,
            onContactClick = {},
            onAvailabilityClick = {}
        )
    }
}
