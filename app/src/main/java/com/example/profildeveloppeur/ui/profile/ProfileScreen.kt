package com.example.profildeveloppeur.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            // innerPadding évite les barres système, le contentPadding écarte le contenu des bords
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            // Espace identique entre toutes les sections
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item(key = "header") {
                ProfileHeader(name = profile.name, role = profile.role)
            }

            item(key = "description") {
                Text(
                    text = profile.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item(key = "skills") {
                Column {
                    SectionTitle(title = "Compétences")
                    Spacer(modifier = Modifier.height(8.dp))
                    profile.skills.forEach { skill ->
                        SkillItem(
                            skill = skill.name,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            item(key = "projects-title") {
                SectionTitle(title = "Projets & expériences")
            }
            // Un item de la LazyColumn par projet (affichage détaillé à venir avec ProjectCard)
            items(items = profile.projects, key = { it.title }) { project ->
                Text(
                    text = "${project.title} · ${project.period}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item(key = "contact") {
                Column {
                    SectionTitle(title = "Informations")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoLine(label = "Contact", value = profile.email)
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoLine(label = "Localisation", value = profile.location)
                }
            }

            item(key = "actions") {
                Column {
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
