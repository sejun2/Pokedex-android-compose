package com.example.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Initial)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    private val _selectedPokemonIndex = MutableStateFlow(-1)
    val selectedPokemonIndex = _selectedPokemonIndex.asStateFlow()

    private val _pokemonList = MutableStateFlow(ArrayList<PokemonDetail>())
    val pokemonList = _pokemonList.asStateFlow()

    suspend fun setPokemonIndex(pokemonIndex: Int) {
        fetchPokemonDetail(pokemonIndex)
        _selectedPokemonIndex.value = pokemonIndex
    }

    // fetch detail model list
    suspend fun fetchPokemonDetail(pokemonIndex: Int) {
        withContext(Dispatchers.IO) {
            try {
                val tmpList: ArrayList<PokemonDetail> = _pokemonList.value.clone() as ArrayList<PokemonDetail>

                if (pokemonIndex - 1 > 0)
                    getPokemonDetailUseCase.execute(pokemonIndex - 1).collect {
                        if (_pokemonList.value.contains(it)) return@collect
                        tmpList.add(0,it)
                    }

                getPokemonDetailUseCase.execute(pokemonIndex).collect {
                    if (_pokemonList.value.contains(it)) return@collect
                    tmpList.add(it)
                }

                if (pokemonIndex + 1 > 0)
                    getPokemonDetailUseCase.execute(pokemonIndex + 1).collect {
                        if (_pokemonList.value.contains(it)) return@collect
                        tmpList.add(it)
                    }

                _pokemonList.value = tmpList

                _uiState.value = PokemonDetailUiState.Success(
                    data = _pokemonList.value,
                )
            } catch (e: Exception) {
                _uiState.value = PokemonDetailUiState.Error(e.toString())
            }
        }
    }

    fun fetchPokemonDetail(pokemonName: String) {
//        viewModelScope.launch {
//            getPokemonDetailUseCase.execute(pokemonName = pokemonName)
//                .onStart {
//                    _uiState.value = PokemonDetailUiState.Loading
//                }.catch { e ->
//                    _uiState.value = PokemonDetailUiState.Error(e.stackTraceToString())
//                }.collect { data ->
//                    _uiState.value = PokemonDetailUiState.Success(
//                        data = data,
//                    )
//                }
//        }
    }

    suspend fun fetchNextPokemonDetail() {
        this._selectedPokemonIndex.value++
        fetchPokemonDetail(selectedPokemonIndex.value)
    }

    suspend fun fetchPreviousPokemonDetail() {
        this._selectedPokemonIndex.value--
        fetchPokemonDetail(selectedPokemonIndex.value)
    }
}

sealed class PokemonDetailUiState {
    data object Initial : PokemonDetailUiState()
    data object Loading : PokemonDetailUiState()
    data class Error(val msg: String) : PokemonDetailUiState()
    data class Success(
        val data: List<PokemonDetail>,
    ) :
        PokemonDetailUiState()
}
