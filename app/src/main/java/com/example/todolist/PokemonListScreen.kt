package com.example.todolist

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todolist.domain.model.PokemonSummary
import com.example.todolist.ui.theme.TodolistTheme

@Composable
fun PokemonListScreen() {
    TodolistTheme {
        Text(text = "This is home screen")
    }
}

@Composable
fun PokemonListItem(pokemonData: PokemonSummary) {
    Box(modifier = Modifier) {

    }
}

@Composable
fun PokemonListView(pokemonDataList: List<PokemonSummary>) {

}