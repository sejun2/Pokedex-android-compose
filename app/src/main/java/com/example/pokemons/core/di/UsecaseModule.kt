package com.example.pokemons.core.di

import com.example.pokemons.domain.repository.IPokemonRepository
import com.example.pokemons.domain.usecase.GetPokemonDetailUseCase
import com.example.pokemons.domain.usecase.GetPokemonSummaryListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    fun provideGetPokemonSummaryUsecase(repository: IPokemonRepository): GetPokemonSummaryListUseCase {
        return GetPokemonSummaryListUseCase(repository)
    }

    @Provides
    fun provideGetPokemonDetailUsecase(repository: IPokemonRepository): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCase(repository)
    }
}