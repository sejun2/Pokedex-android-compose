package com.example.pokemons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemons.core.navigation.NavigationActions
import com.example.pokemons.core.navigation.NavigationScreen
import com.example.pokemons.presentation.screen.PokemonDetailScreen
import com.example.pokemons.presentation.screen.PokemonListScreen
import com.example.pokemons.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                val navController = rememberNavController()
                val navigationActions = rememberNavigationActions(navController = navController)

                NavHost(
                    navController = navController,
                    startDestination = NavigationScreen.NavPokemonList.route
                ) {
                    composable(route = NavigationScreen.NavPokemonList.route) {
                        PokemonListScreen(navigateToPokemonDetail = { pokemonId ->
                            navigationActions.navigateToPokemonDetail(pokemonId = pokemonId)
                        })
                    }
                    composable(
                        route = NavigationScreen.NavPokemonDetail.route,
                        arguments = listOf(navArgument("pokemonId") {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->
                        val pokemonId =
                            navBackStackEntry.arguments?.getInt("pokemonId") ?: return@composable
                        PokemonDetailScreen(
                            pokemonId = pokemonId,
                            onNavigateUp = {
                                navigationActions.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun rememberNavigationActions(navController: NavController = rememberNavController()): NavigationActions {
    return remember(navController) { NavigationActions(navController) }
}