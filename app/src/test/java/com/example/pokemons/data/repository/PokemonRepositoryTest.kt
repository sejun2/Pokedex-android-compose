package com.example.pokemons.data.repository

import com.example.pokemons.core.api.PokemonApiService
import com.google.gson.Gson
import io.mockk.mockk
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test


class PokemonRepositoryTest {
    private lateinit var pokemonApiService: PokemonApiService
    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setUp() {
        pokemonApiService = mockk()
        pokemonRepository = PokemonRepository(pokemonApiService)
    }
}