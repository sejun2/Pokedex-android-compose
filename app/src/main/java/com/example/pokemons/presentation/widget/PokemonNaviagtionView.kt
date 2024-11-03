package com.example.pokemons.presentation.widget

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import com.example.pokemons.domain.model.PokemonDetail
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonNavigationView(
    modifier: Modifier,
    pokemonList: List<PokemonDetail>,
    selectedPokemonIndex: Int,
    onPageMoved: (Int) -> Unit,
) {
    Log.d("PokemonNavigationView", "$pokemonList $selectedPokemonIndex")
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

    val pagerState = rememberPagerState(
        initialPage = if (selectedPokemonIndex == 1) 0 else 1,
        initialPageOffsetFraction = 0.0f,
        pageCount = {
            pokemonList.size
        },
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageMoved(pokemonList.get(pagerState.currentPage).index)
    }
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,  // 아이템 간의 간격
        modifier = Modifier.zIndex(2f),
        contentPadding = PaddingValues(150.dp),
        key = { page -> pokemonList[page].index }
    ) { page ->
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(8.dp)
                .graphicsLayer {
                    // 현재 페이지와의 거리 계산
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // 선택되지 않은 아이템은 작게, 흐리게 표시
                    scaleX = 1f - (pageOffset * 0.5f)
                    scaleY = 1f - (pageOffset * 0.5f)
                    alpha = 1f - (pageOffset * 0.6f)
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
            ) {
                SubcomposeAsyncImage(
                    model = pokemonList.get(page).imageSrc,
                    contentDescription = "image",
                    alignment = Alignment.Center,
                    imageLoader = imageLoader,
                    success = {
                        Image(
                            painter = it.painter,
                            contentDescription = "Loading",
                            modifier = Modifier
                                .width(190.dp)
                                .height(190.dp)
                                .zIndex(1f)
                        )
                    },
                )
            }
        }
    }
}