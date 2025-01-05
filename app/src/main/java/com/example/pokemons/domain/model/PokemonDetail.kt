package com.example.pokemons.domain.model

import com.example.pokemons.presentation.widget.PokemonType
import com.example.pokemons.util.capitalizeFirstLowercaseRest
import com.example.pokemons.util.hyphenToUnderscore
import kotlinx.collections.immutable.ImmutableList

data class PokemonDetail(
    val name: String,
    val index: Int,
    val image: String?,
    val types: ImmutableList<PokemonType>,
    val weight: Double,
    val height: Double,
    val moves: ImmutableList<String>,
    val description: String,
    val statsList: ImmutableList<Stats>,
    val imageSrc: String = "https://projectpokemon.org/images/normal-sprite/${
        name.lowercase().hyphenToUnderscore()
    }.gif"
) {
    fun toPrettyMoves(): String {
        val sb: StringBuilder = StringBuilder()

        for (i: Int in 0 until this.moves.size) {
            sb.append(moves[i].capitalizeFirstLowercaseRest())

            if (i != moves.size - 1) {
                sb.append("\n")
            }
        }

        return sb.toString()
    }
}

data class Stats(
    val name: String,
    val value: Int,
    val effort: Int,
    val url: String,
) {
    fun toPrettyName() =
        when (this.name) {
            "hp" -> "HP"
            "attack" -> "ATK"
            "defense" -> "DEF"
            "special-attack" -> "SATK"
            "special-defense" -> "SDEF"
            "speed" -> "SPD"
            else -> "NaS"
        }

}