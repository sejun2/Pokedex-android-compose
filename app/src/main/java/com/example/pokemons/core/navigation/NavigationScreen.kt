package com.example.pokemons.core.navigation

sealed class NavigationScreen(val route: String) {
    data object NavPokemonList : NavigationScreen("pokemon_list")
    data object NavPokemonDetail : NavigationScreen("pokemon_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int) = "pokemon_detail/$pokemonId"
    }
}