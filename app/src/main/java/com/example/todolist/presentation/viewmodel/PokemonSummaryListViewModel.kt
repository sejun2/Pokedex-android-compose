package com.example.todolist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.PokemonSummary
import com.example.todolist.domain.usecase.GetPokemonSummaryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSummaryListViewModel @Inject constructor(private val getPokemonSummaryListUseCase: GetPokemonSummaryListUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private var currentOffset = 1
    private val limit = 30
    private var isLoading = false
    private val _pokemonList = mutableListOf<PokemonSummary>()

    fun fetchPokemonList() {
        Log.d("ViewModel", "fetch $isLoading")
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true

            getPokemonSummaryListUseCase.execute(currentOffset, limit)
                .onStart {
                    if (currentOffset == 0) {
                        _uiState.value = PokemonListUiState.Loading
                    }
                }
                .catch { e ->
                    _uiState.value =
                        PokemonListUiState.Error(e.message ?: "Unknown error occurred")
                    isLoading = false
                }
                .collect { pokemonList ->
                    Log.d("poke", "$limit $currentOffset")

                    _pokemonList.addAll(pokemonList.pokemonSummaryList)
                    _uiState.value = PokemonListUiState.Success(_pokemonList.toList())
                    currentOffset += limit
                    isLoading = false
                }
        }
    }

    fun loadMore() {
        fetchPokemonList()
    }
}

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemonList: List<PokemonSummary>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
