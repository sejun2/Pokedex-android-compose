package com.example.todolist.presentation.viewmodel

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

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        viewModelScope.launch {
            getPokemonSummaryListUseCase.execute(100, 30)
                .onStart { _uiState.value = PokemonListUiState.Loading }
                .catch { e ->
                    _uiState.value = PokemonListUiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { pokemonList ->
                    _uiState.value = PokemonListUiState.Success(pokemonList.pokemonSummaryList)
                }
        }
    }
}

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemonList: List<PokemonSummary>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
