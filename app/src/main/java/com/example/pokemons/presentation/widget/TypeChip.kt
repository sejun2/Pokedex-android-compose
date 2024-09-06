package com.example.pokemons.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokemons.ui.theme.Bug
import com.example.pokemons.ui.theme.Dark
import com.example.pokemons.ui.theme.Dragon
import com.example.pokemons.ui.theme.Electric
import com.example.pokemons.ui.theme.Fairy
import com.example.pokemons.ui.theme.Fighting
import com.example.pokemons.ui.theme.Fire
import com.example.pokemons.ui.theme.Flying
import com.example.pokemons.ui.theme.Ghost
import com.example.pokemons.ui.theme.Grass
import com.example.pokemons.ui.theme.Ground
import com.example.pokemons.ui.theme.Ice
import com.example.pokemons.ui.theme.Normal
import com.example.pokemons.ui.theme.Poison
import com.example.pokemons.ui.theme.Psychic
import com.example.pokemons.ui.theme.Rock
import com.example.pokemons.ui.theme.Steel
import com.example.pokemons.ui.theme.Water
import com.example.pokemons.util.capitalizeFirstKeepRest

enum class PokemonType(val color: Color) {
    BUG(
        color = Bug
    ),
    DARK(
        color = Dark
    ),
    DRAGON(
        color = Dragon
    ),
    ELECTRIC(
        color = Electric
    ),
    FAIRY(
        color = Fairy
    ),
    FIGHTING(
        color = Fighting
    ),
    FIRE(
        color = Fire
    ),
    FLYING(
        color = Flying
    ),
    GHOST(
        color = Ghost
    ),
    NORMAL(
        color = Normal
    ),
    GRASS(
        color = Grass
    ),
    WATER(
        color = Water
    ),
    POISON(
        color = Poison
    ),
    GROUND(
        color = Ground
    ),
    ICE(
        color = Ice
    ),
    PSYCHIC(
        color = Psychic
    ),
    ROCK(
        color = Rock
    ),
    STEEL(
        color = Steel
    )
}

@Composable
fun TypeChip(pokemonType: PokemonType) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = pokemonType.color)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            pokemonType.name.capitalizeFirstKeepRest(),
            style = TextStyle(
                fontWeight = FontWeight.W800,
                color = White,
            ),
        )
    }
}