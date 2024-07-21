package com.dcs.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.ui.AllAboutMovieApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllAboutMovieTheme {
                AllAboutMovieApp()
            }
        }
    }
}
