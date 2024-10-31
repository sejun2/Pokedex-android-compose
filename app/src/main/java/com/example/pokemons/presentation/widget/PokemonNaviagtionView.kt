package com.example.pokemons.presentation.widget

import android.os.Build.VERSION.SDK_INT
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import com.example.pokemons.R
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.presentation.screen.PokemonImageView


@Composable
fun PokemonNavigationView(
    modifier: Modifier,
    pokemon: PokemonDetail,
    prevPokemon: PokemonDetail? = null,
    nextPokemon: PokemonDetail? = null,
    hasNextPokemon: Boolean,
    onNextPokemonButtonClick: () -> Unit,
    onPreviousPokemonButtonClick: () -> Unit,
    hasPreviousPokemon: Boolean
) {
    val localContext = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context = localContext)
            .diskCache {
                DiskCache.Builder().directory(
                    directory = localContext.filesDir
                ).build()
            }.components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    Row(
        modifier = modifier
            .padding(vertical = 2.dp)
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (hasPreviousPokemon)
            Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
                SubcomposeAsyncImage(
                    model = prevPokemon?.imageSrc,
                    contentDescription = "image",
                    alignment = Alignment.Center,
                    imageLoader = imageLoader,
                    success = {
                        Image(
                            painter = it.painter,
                            contentDescription = "Loading",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .zIndex(1f)
                                .alpha(0.5f),
                            colorFilter = ColorFilter.tint(
                                Gray, blendMode = BlendMode.SrcIn
                            )
                        )
                    },
                )
                IconButton(
                    onClick = onPreviousPokemonButtonClick

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "left_arrow",
                        tint = White,
                        modifier = Modifier
                            .width(50.dp)
                    )
                }
            } else {
            Box(modifier = Modifier.width(50.dp))
        }
        Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center){
            PokemonImageView(pokemonDetail = pokemon)
        }
        if (hasNextPokemon)
            Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
                SubcomposeAsyncImage(
                    model = nextPokemon?.imageSrc,
                    contentDescription = "image",
                    alignment = Alignment.Center,
                    imageLoader = imageLoader,
                    success = {
                        Image(
                            painter = it.painter,
                            contentDescription = "Loading",
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .zIndex(1f)
                                .alpha(0.5f),
                            colorFilter = ColorFilter.tint(
                                Gray, blendMode = BlendMode.SrcIn
                            )
                        )
                    },
                )
                IconButton(
                    onClick = onNextPokemonButtonClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "right_arrow",
                        tint = White,
                        modifier = Modifier
                            .width(50.dp)
                    )
                }
            }
        else {
            Box(modifier = Modifier.width(50.dp))
        }
    }
}