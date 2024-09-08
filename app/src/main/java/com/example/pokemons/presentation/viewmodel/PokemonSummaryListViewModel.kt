package com.example.pokemons.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemons.domain.model.PokemonSummary
import com.example.pokemons.domain.usecase.GetPokemonSummaryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSummaryListViewModel @Inject constructor(private val getPokemonSummaryListUseCase: GetPokemonSummaryListUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    private var currentOffset = 0
    private val limit = 30
    private var isLoading = false
    private val _pokemonList = mutableListOf<PokemonSummary>()

    init {
        fetchPokemonList()
        setOnSearchValueChange()
    }

    private fun fetchPokemonList() {
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
                    Log.d("poke::", "$pokemonList")

                    _pokemonList.addAll(pokemonList.pokemonSummaryList)
                    _uiState.value = PokemonListUiState.Success(_pokemonList.toList())
                    currentOffset += limit
                    filterPokemonList()
                    isLoading = false
                }
        }
    }

    fun loadMore() {
        Log.d("SummaryListViewModel", "loadmore")
        fetchPokemonList()
    }

    fun search(value: String) {
        _searchValue.value = value
    }

    @OptIn(FlowPreview::class)
    private fun setOnSearchValueChange() {
        _searchValue.debounce(340).distinctUntilChanged().onEach {
            filterPokemonList()
        }.launchIn(viewModelScope)
    }

    private fun filterPokemonList() {
        if (_uiState.value is PokemonListUiState.Success) {
            val filteredList = _pokemonList.filter {
                it.name.contains(searchValue.value, ignoreCase = true)
            }

            _uiState.value = PokemonListUiState.Success(
                pokemonList = filteredList
            )
        }
    }
}

sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(val pokemonList: List<PokemonSummary>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
