package com.example.todolist.data.repository

import com.example.todolist.core.api.PokemonApiService
import com.example.todolist.data.dto.PokemonListDto
import com.example.todolist.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(val pokemonApiService: PokemonApiService) :
    IPokemonRepository {
    override fun getPokemonList(): Flow<PokemonListDto> {
        TODO("Not yet implemented")
    }
}