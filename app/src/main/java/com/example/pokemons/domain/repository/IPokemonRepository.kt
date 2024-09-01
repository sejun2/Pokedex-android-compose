package com.example.pokemons.domain.repository

import com.example.pokemons.domain.model.PokemonSummaryList
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList>
    suspend fun getPokemonDetail()
}