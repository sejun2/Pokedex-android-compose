package com.example.todolist.core.di

import com.example.todolist.core.api.PokemonApiService
import com.example.todolist.data.repository.PokemonRepository
import com.example.todolist.domain.repository.IPokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePokemonRepository(pokemonApiService: PokemonApiService): IPokemonRepository {
        return PokemonRepository(pokemonApiService = pokemonApiService)
    }
}