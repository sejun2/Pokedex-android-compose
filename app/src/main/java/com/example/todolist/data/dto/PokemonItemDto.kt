package com.example.todolist.data.dto

import com.example.todolist.domain.model.PokemonSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

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