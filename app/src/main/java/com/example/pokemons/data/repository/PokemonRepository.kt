package com.example.pokemons.data.repository

import com.example.pokemons.core.api.PokemonApiService
import com.example.pokemons.data.dto.toDomain
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.PokemonSummaryList
import com.example.pokemons.domain.repository.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonApiService: PokemonApiService) :
    IPokemonRepository {
    private val pokemonDetailCache = mutableMapOf<Int, PokemonDetail>()

    override suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList> = flow {
        val res = pokemonApiService.getPokemonSummaryList(offset, limit)
        emit(res.toDomain())
    }

    override suspend fun getPokemonDetail(pokemonIndex: Int): Flow<PokemonDetail> = flow {
        val cachedDetail = getPokemonDetailFromCacheOrNull(pokemonIndex)

        if (cachedDetail != null) {
            emit(cachedDetail)
        } else {
            val res = pokemonApiService.getPokemonDetail(pokemonIndex)
            val pokemonDetail = res.toDomain()
            setPokemonDetailForCache(pokemonDetail)
            emit(pokemonDetail)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPokemonDetail(pokemonName: String): Flow<PokemonDetail> = flow {
        val res = pokemonApiService.getPokemonDetail(pokemonName)
        emit(res.toDomain())
    }.flowOn(Dispatchers.IO)

    private fun getPokemonDetailFromCacheOrNull(pokemonIndex: Int): PokemonDetail? =
        pokemonDetailCache[pokemonIndex]


    private fun setPokemonDetailForCache(pokemonDetail: PokemonDetail) {
        pokemonDetailCache[pokemonDetail.index] = pokemonDetail
    }
}