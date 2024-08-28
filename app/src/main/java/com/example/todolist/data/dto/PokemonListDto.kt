package com.example.todolist.data.dto

import com.example.todolist.domain.model.PokemonSummaryList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String,
    @SerialName("previous")
    val previous: String,
    @SerialName("results")
    val results: List<PokemonItemDto>
)

fun PokemonListDto.toDomain(): PokemonSummaryList {
    return PokemonSummaryList(
        next = this.next,
        count = this.count,
        previous = this.previous,
        pokemonSummaryList = this.results.map {
            it.toDomain()
        }
    )
}