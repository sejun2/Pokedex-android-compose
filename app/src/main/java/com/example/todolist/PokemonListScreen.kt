package com.example.todolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.domain.model.PokemonSummary
import com.example.todolist.presentation.viewmodel.PokemonListUiState
import com.example.todolist.presentation.viewmodel.PokemonSummaryListViewModel
import com.example.todolist.ui.theme.TodolistTheme

@Composable
fun PokemonListScreen() {
    TodolistTheme {
        PokemonListView()
    }
}

@Composable
fun PokemonListItem(pokemonData: PokemonSummary) {
    Box(modifier = Modifier) {
        Text(text = "${pokemonData.name}\n${pokemonData.url}")
    }
}

@Composable
fun PokemonListView(pokemonSummaryListViewModel: PokemonSummaryListViewModel = hiltViewModel()) {
    val uiState by pokemonSummaryListViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "a") {
        pokemonSummaryListViewModel.fetchPokemonList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Pokemon List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = uiState) {
            is PokemonListUiState.Loading -> CircularProgressIndicator()
            is PokemonListUiState.Error -> Text(
                "Error: ${state.toString()}",
                color = MaterialTheme.colorScheme.error
            )

            is PokemonListUiState.Success -> PokemonList(state.pokemonList)
        }
    }
}

@Composable
fun PokemonList(pokemonList: List<PokemonSummary>) {
    LazyColumn {
        items(pokemonList) { pokemon ->
            PokemonListItem(pokemon)
        }
    }
}