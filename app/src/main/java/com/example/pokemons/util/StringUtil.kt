package com.example.pokemons.util

/**
 * The pokedexIndexString length should have at least 3.
 * If length is shorter than 3, then empty section of each string digits fill as "0"
 * ex)
 * val index = "23"
 * val processedIndex = index.toPokedexIndex()
 *
 * `processedIndex.equals("023")`
 */
fun String.toPokedexIndex(): String {
    val result = StringBuilder(this)

    for (i in 0 until 3 - this.length) {
        result.insert(0, "0")
    }

    return result.toString()
}

/**
 * Capitalize first String and lowercase rest String
 * ex)
 * val data = "butterFree"
 * val processedData = data.capitalizeFirstLowercaseRest()
 *
 * `processedData.equals("Butterfree")`
 */
fun String.capitalizeFirstLowercaseRest(): String {
    return if (this.isEmpty()) {
        this
    } else {
        this[0].uppercase() + this.substring(1).lowercase()
    }
}

fun String.hyphenToUnderscore(): String {
    return this.replace("-", "_")
}