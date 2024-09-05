package com.example.pokemons.util

fun String.toPokedexIndex(): String {
    var result = this

    if (this.length < 3) {
        val count = 3 - this.length

        for (i in 0..<count) {
            result = "0$result"
        }
    }

    return result
}