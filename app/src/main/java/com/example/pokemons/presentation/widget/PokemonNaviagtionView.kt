package com.example.pokemons.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.presentation.screen.PokemonImageView


@Composable
fun PokemonNavigationView(
    modifier: Modifier,
    pokemon: PokemonDetail,
    hasNextPokemon: Boolean,
    onNextPokemonButtonClick: () -> Unit,
    onPreviousPokemonButtonClick: () -> Unit,
    hasPreviousPokemon: Boolean
) {
    Row(
        modifier = modifier
            .padding(vertical = 2.dp)
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (hasPreviousPokemon)
            IconButton(
                onClick = onPreviousPokemonButtonClick

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "left_arrow",
                    tint = White,
                    modifier = Modifier
                        .width(50.dp)
                )
            } else {
            Box(modifier = Modifier.width(50.dp))
        }
        PokemonImageView(pokemonDetail = pokemon)
        if (hasNextPokemon)
            IconButton(
                onClick = onNextPokemonButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "right_arrow",
                    tint = White,
                    modifier = Modifier
                        .width(50.dp)
                )
            }
        else {
            Box(modifier = Modifier.width(50.dp))
        }
    }
}