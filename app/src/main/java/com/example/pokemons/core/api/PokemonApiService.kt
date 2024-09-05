package com.example.pokemons.core.api

import com.example.pokemons.data.dto.PokemonDetailDto
import com.example.pokemons.data.dto.PokemonListDto
import com.example.pokemons.domain.model.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonSummaryList(
        @Query("offset") offset: Int, @Query("limit") limit: Int
    ): PokemonListDto

    @GET("pokemon/{pokemonIndex}")
    suspend fun getPokemonDetail(
        @Path("pokemonIndex") pokemonIndex: Int
    ): PokemonDetailDto

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(
        @Path("pokemonName") pokemonName: String
    ): PokemonDetailDto
}