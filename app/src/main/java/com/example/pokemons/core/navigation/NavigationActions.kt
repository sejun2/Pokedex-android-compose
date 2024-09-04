package com.example.pokemons.core.navigation

import androidx.navigation.NavController

class NavigationActions(private val navController: NavController) {
    fun navigateToPokemonDetail(pokemonId: Int) {
        navController.navigate(NavigationScreen.NavPokemonDetail.createRoute(pokemonId))
    }

    fun navigateToPokemonList() {
        navController.navigate(NavigationScreen.NavPokemonList.route)
    }

    fun navigateUp() = navController.navigateUp()
}