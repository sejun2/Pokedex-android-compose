package com.example.pokemons.data.dto

import android.os.Parcelable
import com.example.pokemons.domain.model.PokemonSummary
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class PokemonItemDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
    val index: Int,
) : Parcelable

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