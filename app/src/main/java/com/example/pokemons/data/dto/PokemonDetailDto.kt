package com.example.pokemons.data.dto

import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.Stats
import com.example.pokemons.presentation.widget.PokemonType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val name: String,
    val id: Int,
    val height: Double,
    val weight: Double,
    val abilities: List<AbilityDto>,
    val stats: List<StatDto>,
    val types: List<TypeDto>
)

@Serializable
data class TypeDto(
    val slot: Int,
    val type: Type,
)

@Serializable
data class Type(
    val name: String,
    val url: String
)


@Serializable
data class AbilityDto(
    val ability: Ability,
    @SerialName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
)

@Serializable
data class Ability(
    val name: String,
    val url: String
)

@Serializable
data class StatDto(
    @SerialName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: Stat
)

@Serializable
data class Stat(
    val name: String,
    val url: String
)

//TODO: Fix description
fun PokemonDetailDto.toDomain(): PokemonDetail {
    val pokemonType: List<PokemonType> =
        this.types.map { it ->
            try {
                PokemonType.valueOf(it.type.name.uppercase())
            } catch (e: Exception) {
                PokemonType.NORMAL
            }
        }

    val stats: List<Stats> =
        this.stats.map { it ->
            Stats(
                name = it.stat.name,
                value = it.baseStat,
                url = it.stat.url,
                effort = it.effort,
            )
        }

    return PokemonDetail(
        index = id,
        name = name,
        image = null,
        types = pokemonType,
        statsList = stats,
        description = "Temporal description",
        moves = this.abilities.map { it.ability.name },
        height = height,
        weight = weight
    )
}