package com.example.pokemons.data.dto

import android.os.Parcelable
import com.example.pokemons.domain.model.PokemonSummary
import com.example.pokemons.domain.model.PokemonSummaryList
import com.google.gson.annotations.SerializedName
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.internal.immutableListOf

@Parcelize
data class PokemonListDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val results: List<PokemonItemDto>?
) : Parcelable

fun PokemonListDto.toDomain(): PokemonSummaryList {
    return PokemonSummaryList(
        next = this.next,
        count = this.count,
        previous = this.previous,
        pokemonSummaryList = this.results?.map {
            it.toDomain()
        }?.toImmutableList() ?: emptyList<PokemonSummary>().toImmutableList()
    )
}