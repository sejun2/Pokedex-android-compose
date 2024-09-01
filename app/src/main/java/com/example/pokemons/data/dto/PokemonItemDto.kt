package com.example.pokemons.data.dto

import com.example.pokemons.domain.model.PokemonSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonItemDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
    val index: Int,
)

fun PokemonItemDto.toDomain(): PokemonSummary {
    return PokemonSummary(
        name = this.name,
        url = this.url,
        index = this.index
    )
}