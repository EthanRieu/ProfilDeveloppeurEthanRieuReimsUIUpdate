package com.example.profildeveloppeur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.profildeveloppeur.model.androidJuniorProfile
import com.example.profildeveloppeur.ui.profile.ProfileScreen
import com.example.profildeveloppeur.ui.theme.ProfilDeveloppeurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilDeveloppeurTheme {
                ProfileScreen(
                    profile = androidJuniorProfile,
                    onContactClick = {
                        // Callback volontairement vide (hors périmètre du devoir) :
                        // plus tard, ouvrir l'application mail avec l'adresse du profil
                    },
                    onPortfolioClick = {
                        // Callback volontairement vide (hors périmètre du devoir) :
                        // plus tard, ouvrir le lien du portfolio dans le navigateur
                    }
                )
            }
        }
    }
}
