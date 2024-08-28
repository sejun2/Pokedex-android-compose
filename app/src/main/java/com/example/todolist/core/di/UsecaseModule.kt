package com.example.todolist.core.di

import com.example.todolist.domain.repository.IPokemonRepository
import com.example.todolist.domain.usecase.GetPokemonSummaryListUseCase
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
}