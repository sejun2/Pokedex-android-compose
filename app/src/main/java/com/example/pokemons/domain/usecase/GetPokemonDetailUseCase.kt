package com.example.pokemons.domain.usecase

import com.example.pokemons.domain.repository.IPokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(private val pokemonRepository: IPokemonRepository) {
    suspend fun execute(pokemonName: String) = pokemonRepository.getPokemonDetail(pokemonName)
    suspend fun execute(pokemonIndex: Int) = pokemonRepository.getPokemonDetail(pokemonIndex)
}