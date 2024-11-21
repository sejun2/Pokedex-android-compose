package com.example.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Initial)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()


    private val _selectedPokemonIndex = MutableStateFlow(-1)
    val selectedPokemonIndex = _selectedPokemonIndex.asStateFlow()

    private var _pokemonList = arrayListOf<PokemonDetail>()

    suspend fun setPokemonIndex(pokemonIndex: Int) {
        _selectedPokemonIndex.value = pokemonIndex
        fetchPokemonDetail(pokemonIndex)
    }

    // fetch detail model list
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchPokemonDetail(pokemonIndex: Int) {
        withContext(Dispatchers.IO) {
            val tmpList = _pokemonList.clone() as ArrayList<PokemonDetail>

            val flowList = listOfNotNull(
                getPokemonDetailUseCase.execute(pokemonIndex),
                if (pokemonIndex - 1 > 0)
                    getPokemonDetailUseCase.execute(pokemonIndex - 1) else null,
                if (pokemonIndex + 1 > 0)
                    getPokemonDetailUseCase.execute(pokemonIndex + 1) else null,
            )

            // parallel execution
            flowList.merge().collect { item ->
                if (item !in tmpList) {
                    tmpList.add(item)
                    tmpList.sortBy { it.index }  // 새 항목이 추가될 때만 정렬
                }
            }.runCatching {
                _pokemonList = tmpList

                _uiState.value = PokemonDetailUiState.Success(
                    data = _pokemonList,
                )
            }.onFailure {
                _errorEvent.emit("fetch pokemonDetail failed... please try again")
            }

        }
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
