package com.example.pokemons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import com.example.pokemons.presentation.screen.PokemonDetailScreen
import com.example.pokemons.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                Column {
//                    PokemonListScreen()
                    PokemonDetailScreen()
                }
            }
        }
    }
}