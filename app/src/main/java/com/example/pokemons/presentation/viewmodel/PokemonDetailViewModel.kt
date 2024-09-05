package com.example.pokemons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokemons.domain.model.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PokemonDetailViewModel : ViewModel() {
    // pokemon detail model list variable

    // fetch detail model list
}

sealed class PokemonDetailUiState {
    object Loading
    data class Error(val msg: String)
    data class Success(val data: List<PokemonDetail>)
}