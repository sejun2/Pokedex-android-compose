package com.example.pokemons.data.repository

import com.example.pokemons.core.api.PokemonApiService
import com.example.pokemons.data.dto.PokemonDetailDto
import com.example.pokemons.data.dto.toDomain
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.PokemonSummaryList
import com.example.pokemons.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonApiService: PokemonApiService) :
    IPokemonRepository {
    override suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList> = flow {
        val res = pokemonApiService.getPokemonSummaryList(offset, limit)
        emit(res.toDomain())
    }

    override suspend fun getPokemonDetail(pokemonIndex: Int): Flow<PokemonDetail> = flow {
        val res = pokemonApiService.getPokemonDetail(pokemonIndex)
        emit(res.toDomain())
    }

    override suspend fun getPokemonDetail(pokemonName: String): Flow<PokemonDetail> = flow {
        val res = pokemonApiService.getPokemonDetail(pokemonName)
        emit(res.toDomain())
    }
}