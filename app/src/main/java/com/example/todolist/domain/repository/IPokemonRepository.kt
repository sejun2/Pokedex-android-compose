package com.example.todolist.domain.repository

import com.example.todolist.domain.model.PokemonSummaryList
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonSummaryList>
    suspend fun getPokemonDetail()
}