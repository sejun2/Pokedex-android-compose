package com.example.todolist.core.api

import com.example.todolist.data.dto.PokemonListDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonSummaryList(
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): PokemonListDto
}