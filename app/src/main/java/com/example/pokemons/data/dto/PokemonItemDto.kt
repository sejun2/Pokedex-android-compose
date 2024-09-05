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
        index = getIndexFromUrl(this.url) ?: 1
    )
}

private fun getIndexFromUrl(url: String): Int? {
    val regex = "/pokemon/(\\d+)/".toRegex()

    return regex.find(url)?.groupValues?.get(1)?.toInt()
}