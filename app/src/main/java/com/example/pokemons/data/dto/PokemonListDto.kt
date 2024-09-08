package com.example.pokemons.data.dto

import android.os.Parcelable
import com.example.pokemons.domain.model.PokemonSummaryList
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class PokemonListDto(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String,
    @SerialName("previous")
    val previous: String,
    @SerialName("results")
    val results: List<PokemonItemDto>?
) : Parcelable

fun PokemonListDto.toDomain(): PokemonSummaryList {
    return PokemonSummaryList(
        next = this.next,
        count = this.count,
        previous = this.previous,
        pokemonSummaryList = this.results?.map {
            it.toDomain()
        } ?: emptyList()
    )
}