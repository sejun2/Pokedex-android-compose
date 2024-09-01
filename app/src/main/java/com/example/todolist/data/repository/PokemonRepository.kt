package com.example.todolist.data.repository

import com.example.todolist.core.api.PokemonApiService
import com.example.todolist.data.dto.toDomain
import com.example.todolist.domain.model.PokemonSummaryList
import com.example.todolist.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonApiService: PokemonApiService) :
    IPokemonRepository {
    override suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList> = flow {
        val res = pokemonApiService.getPokemonSummaryList(offset, limit)
        emit(res.toDomain())
    }

    override suspend fun getPokemonDetail() {
        TODO("Not yet implemented")
    }

}