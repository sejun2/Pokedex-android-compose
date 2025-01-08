package com.example.pokemons.domain.model

data class PokemonSummary(
    val name: String,
    val url: String,
    val index: Int,
    val imageSrc: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png"
)
