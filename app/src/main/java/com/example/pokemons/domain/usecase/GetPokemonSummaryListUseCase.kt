package com.example.pokemons.domain.usecase

import com.example.pokemons.domain.model.PokemonSummaryList
import com.example.pokemons.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonSummaryListUseCase @Inject constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(offset: Int, limit: Int): Flow<PokemonSummaryList> {
        return pokemonRepository.getPokemonList(offset, limit)
            .map { originalList ->
                PokemonSummaryList(
                    pokemonSummaryList = originalList.pokemonSummaryList,
                    count = originalList.count,
                    next = originalList.next,
                    previous = originalList.previous
                )
            }
    }
}