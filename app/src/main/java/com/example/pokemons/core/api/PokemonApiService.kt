package com.example.pokemons.core.api

import com.example.pokemons.data.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonSummaryList(
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): PokemonListDto
}