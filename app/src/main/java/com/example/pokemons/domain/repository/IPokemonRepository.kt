package com.example.pokemons.domain.repository

import com.example.pokemons.data.dto.PokemonDetailDto
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.PokemonSummaryList
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList>
    suspend fun getPokemonDetail(pokemonIndex: Int): Flow<PokemonDetail>
    suspend fun getPokemonDetail(pokemonName: String): Flow<PokemonDetail>
}