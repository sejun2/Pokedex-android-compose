package com.example.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Initial)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    private val _selectedPokemonIndex = MutableStateFlow(-1)
    val selectedPokemonIndex = _selectedPokemonIndex.asStateFlow()

    val hasPreviousPokemon: StateFlow<Boolean> = selectedPokemonIndex.map { it > 1 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val hasNextPokemon: StateFlow<Boolean> = selectedPokemonIndex.map { it in 1..998 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun setPokemonIndex(pokemonIndex: Int) {
        _selectedPokemonIndex.value = pokemonIndex
    }

    // fetch detail model list
    fun fetchPokemonDetail(pokemonIndex: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val currentIndex = selectedPokemonIndex.value

                    var prev: PokemonDetail? = null
                    lateinit var current: PokemonDetail
                    var next: PokemonDetail? = null

                    getPokemonDetailUseCase.execute(currentIndex).collect {
                        current = it
                    }

                    if (currentIndex - 1 > 0)
                        getPokemonDetailUseCase.execute(currentIndex - 1).collect {
                            prev = it
                        }
                    if (currentIndex + 1 > 0)
                        getPokemonDetailUseCase.execute(currentIndex + 1).collect {
                            next = it
                        }

                    _uiState.value = PokemonDetailUiState.Success(
                        data = current,
                        nextData = next,
                        prevData = prev
                    )
                } catch (e: Exception) {
                    _uiState.value = PokemonDetailUiState.Error(e.toString())
                }
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
                        data = data,
                    )
                }
        }
    }

    fun fetchNextPokemonDetail() {
        this._selectedPokemonIndex.value++
        fetchPokemonDetail(selectedPokemonIndex.value)
    }

    fun fetchPreviousPokemonDetail() {
        this._selectedPokemonIndex.value--
        fetchPokemonDetail(selectedPokemonIndex.value)
    }
}

sealed class PokemonDetailUiState {
    data object Initial : PokemonDetailUiState()
    data object Loading : PokemonDetailUiState()
    data class Error(val msg: String) : PokemonDetailUiState()
    data class Success(
        val data: PokemonDetail,
        val prevData: PokemonDetail? = null,
        val nextData: PokemonDetail? = null
    ) :
        PokemonDetailUiState()
}
