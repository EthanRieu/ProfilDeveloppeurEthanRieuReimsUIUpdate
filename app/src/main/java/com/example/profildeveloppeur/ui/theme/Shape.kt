package com.example.profildeveloppeur.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/*
 * Formes Material 3 de l'application, accessibles via MaterialTheme.shapes.
 * - small : pastilles de compétences, petits badges
 * - medium : champs, boutons secondaires, zones d'information
 * - large : cartes (projets, sections)
 * - extraLarge : grande zone d'en-tête du profil
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
