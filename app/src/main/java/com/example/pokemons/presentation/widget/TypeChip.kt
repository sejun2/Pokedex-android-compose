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
import com.example.pokemons.ui.theme.Normal
import com.example.pokemons.ui.theme.Purple40
import com.example.pokemons.ui.theme.Water

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
        color = Purple40
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

internal fun String.capitalizeFirstKeepRest(): String {
    return if (this.isEmpty()) {
        this
    } else {
        this[0] + this.substring(1).lowercase()
    }
}
