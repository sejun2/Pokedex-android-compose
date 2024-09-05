package com.example.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Initial)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    // fetch detail model list
    fun fetchPokemonDetail(pokemonIndex: Int) {
        viewModelScope.launch {
            getPokemonDetailUseCase.execute(pokemonIndex = pokemonIndex)
                .onStart {
                    _uiState.value = PokemonDetailUiState.Loading
                }.catch { e ->
                    _uiState.value = PokemonDetailUiState.Error(e.stackTraceToString())
                }.collect { data ->
                    _uiState.value = PokemonDetailUiState.Success(
                        data = data
                    )
                }
        }
    }

    fun fetchPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            getPokemonDetailUseCase.execute(pokemonName = pokemonName)
                .onStart {
                    _uiState.value = PokemonDetailUiState.Loading
                }.catch { e ->
                    _uiState.value = PokemonDetailUiState.Error(e.stackTraceToString())
                }.collect { data ->
                    _uiState.value = PokemonDetailUiState.Success(
                        data = data
                    )
                }
        }
    }
}

sealed class PokemonDetailUiState {
    data object Initial : PokemonDetailUiState()
    data object Loading : PokemonDetailUiState()
    data class Error(val msg: String) : PokemonDetailUiState()
    data class Success(val data: PokemonDetail) : PokemonDetailUiState()
}
