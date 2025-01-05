package com.example.pokemons.domain.model

import kotlinx.collections.immutable.ImmutableList

data class PokemonSummaryList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemonSummaryList: ImmutableList<PokemonSummary>
)
