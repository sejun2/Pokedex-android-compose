package com.example.todolist.domain.repository

import com.example.todolist.data.dto.PokemonListDto
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

    fun getPokemonList(): Flow<PokemonListDto>

//    fun getPokemonDetail(): Flow<PokemonDetailDto>
}