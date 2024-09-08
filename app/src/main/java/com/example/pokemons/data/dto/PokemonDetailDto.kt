package com.example.pokemons.data.dto

import android.os.Parcelable
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.Stats
import com.example.pokemons.presentation.widget.PokemonType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class PokemonDetailDto(
    val name: String,
    val id: Int,
    val height: Double,
    val weight: Double,
    val abilities: List<AbilityDto>,
    val stats: List<StatDto>,
    val types: List<TypeDto>
) : Parcelable

@Parcelize
data class TypeDto(
    val slot: Int,
    val type: Type,
) : Parcelable

@Parcelize
data class Type(
    val name: String,
    val url: String
) : Parcelable


@Parcelize
data class AbilityDto(
    val ability: Ability,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
) : Parcelable

@Parcelize
data class Ability(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class StatDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: Stat
) : Parcelable

@Parcelize
data class Stat(
    val name: String,
    val url: String
) : Parcelable

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
        this.stats.map {
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