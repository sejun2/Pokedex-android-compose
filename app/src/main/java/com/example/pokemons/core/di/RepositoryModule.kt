package com.example.pokemons.core.di

import com.example.pokemons.core.api.PokemonApiService
import com.example.pokemons.data.repository.PokemonRepository
import com.example.pokemons.domain.repository.IPokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonApiService: PokemonApiService): IPokemonRepository {
        return PokemonRepository(pokemonApiService = pokemonApiService)
    }
}