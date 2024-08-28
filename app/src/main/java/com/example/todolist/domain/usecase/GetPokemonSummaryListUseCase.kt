package com.example.todolist.domain.usecase

import com.example.todolist.domain.repository.IPokemonRepository
import javax.inject.Inject

class GetPokemonSummaryListUseCase @Inject constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(offset: Int, limit: Int) =
        pokemonRepository.getPokemonList(offset, limit)
}