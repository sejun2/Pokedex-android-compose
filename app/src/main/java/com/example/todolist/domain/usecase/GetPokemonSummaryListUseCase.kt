package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.PokemonSummaryList
import com.example.todolist.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetPokemonSummaryListUseCase @Inject constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(offset: Int, limit: Int): Flow<PokemonSummaryList> {
        var tmpOffset = offset

        return pokemonRepository.getPokemonList(offset, limit)
            .map { originalList ->
                PokemonSummaryList(
                    pokemonSummaryList = originalList.pokemonSummaryList.map { summary ->
                        summary.copy(index = (tmpOffset++))
                    },
                    count = originalList.count,
                    next = originalList.next,
                    previous = originalList.previous
                )
            }
    }
}