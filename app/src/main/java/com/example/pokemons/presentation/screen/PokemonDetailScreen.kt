package com.example.pokemons.presentation.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import com.example.pokemons.R
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.domain.model.Stats
import com.example.pokemons.presentation.viewmodel.PokemonDetailUiState
import com.example.pokemons.presentation.viewmodel.PokemonDetailViewModel
import com.example.pokemons.presentation.widget.HeadTitleView
import com.example.pokemons.presentation.widget.PokemonDescriptionView
import com.example.pokemons.presentation.widget.PokemonNavigationView
import com.example.pokemons.presentation.widget.PokemonPhysicsView
import com.example.pokemons.presentation.widget.PokemonType
import com.example.pokemons.presentation.widget.PokemonTypeView
import com.example.pokemons.ui.theme.Grayscale
import com.example.pokemons.ui.theme.PokemonTheme
import com.example.pokemons.util.capitalizeFirstLowercaseRest
import com.example.pokemons.util.toPokedexIndex
import kotlinx.coroutines.launch

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    onNavigateUp: () -> Boolean,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pokemonDetailViewModel.setPokemonIndex(pokemonIndex = pokemonId)
        pokemonDetailViewModel.errorEvent.collect { errorMsg ->
            scope.launch {
                SnackbarHostState().showSnackbar(
                    message = errorMsg,
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }

    PokemonTheme {
        PokemonDetailView(onNavigateUp, pokemonId, pokemonDetailViewModel = pokemonDetailViewModel)
    }
}

@Composable
fun PokemonDetailView(
    onNavigateUp: () -> Boolean,
    pokemonId: Int,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val uiState = pokemonDetailViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val navHeight = remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current

            val scope = rememberCoroutineScope()

            // 현재 선택된 포켓몬의 색상을 별도의 상태로 관리
            var backgroundColor by remember { mutableStateOf(Color.White) }

            // uiState가 변경될 때마다 색상 업데이트
            LaunchedEffect(pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value) {
                when (val state = uiState.value) {
                    is PokemonDetailUiState.Success -> {
                        state.data.find { it.index == pokemonDetailViewModel.selectedPokemonIndex.value }
                            ?.let { pokemon ->
                                backgroundColor = pokemon.types[0].color
                            }
                    }

                    else -> {
                        //TODO
                    }
                }
            }

            when (val state = uiState.value) {
                is PokemonDetailUiState.Success -> {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .animatedBackgroundColor(color = backgroundColor)
                    ) {
                        val screenHeight = LocalConfiguration.current.screenHeightDp

                        val guideline = createGuidelineFromTop((screenHeight * 0.3).dp)

                        val (backgroundFieldViewRef, navViewRef, contentViewRef) = createRefs()

                        BackgroundFieldView(
                            Modifier.constrainAs(backgroundFieldViewRef) {
                                top.linkTo(parent.top)
                                bottom.linkTo(guideline)

                                height = Dimension.fillToConstraints
                            },
                            state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }.types[0]
                        )

                        PokemonNavigationView(
                            Modifier
                                .constrainAs(navViewRef) {
                                    top.linkTo(guideline)
                                    bottom.linkTo(guideline)

                                    width = Dimension.matchParent
                                    height = Dimension.wrapContent
                                }
                                .padding(bottom = 16.dp)
                                .zIndex(2f),
                            pokemonList = state.data,
                            selectedPokemonIndex = pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value,
                            onPageMoved = {
                                scope.launch {
                                    pokemonDetailViewModel.setPokemonIndex(it)
                                }
                            }
                        )
                        ContentCardView(
                            Modifier
                                .constrainAs(contentViewRef) {
                                    top.linkTo(guideline)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)

                                    width = Dimension.fillToConstraints
                                    height = Dimension.fillToConstraints
                                }, navHeight = 180.dp,
                            pokemonDetail = state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }
                        )
                    }

                }

                is PokemonDetailUiState.Loading ->
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is PokemonDetailUiState.Initial -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is PokemonDetailUiState.Error ->
                    Text(state.msg)
            }

            Header(onNavigateUp = onNavigateUp, pokemonDetailViewModel = pokemonDetailViewModel)
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Boolean,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val uiState = pokemonDetailViewModel.uiState.collectAsState()
    Row(
        modifier = modifier.padding(end = 12.dp, start = 4.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onNavigateUp() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = White,
                contentDescription = "back_button",
                modifier = Modifier
                    .padding(12.dp)

            )
        }
        when (val state = uiState.value) {
            is PokemonDetailUiState.Success -> {
                Text(
                    state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }.name.capitalizeFirstLowercaseRest(),
                    style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.W900,
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "#${
                        state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }.index.toString()
                            .toPokedexIndex()
                    }", style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.W900,
                        fontSize = 12.sp
                    )
                )
            }

            else -> {
            }
        }
    }

}

fun Modifier.animatedBackgroundColor(color: Color): Modifier = composed() {
    var targetColor = remember { mutableStateOf(Color.White) }
    val backgroundColor by animateColorAsState(
        targetValue = targetColor.value,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing,
            delayMillis = 100,
        ),
        label = "background_color"
    )

    LaunchedEffect(color) {
        targetColor.value = color
    }

    this.background(color = backgroundColor)
}

@Composable
fun BackgroundFieldView(modifier: Modifier, type: PokemonType) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
        ), label = ""
    )

    Box(
        modifier = modifier
            .animatedBackgroundColor(type.color)
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokedex_logo),
            contentDescription = "logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(240.dp)
                .scale(scale),
            alpha = 0.2f
        )
    }
}

@Composable
fun PokemonImageView(modifier: Modifier = Modifier, pokemonDetail: PokemonDetail) {
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

    var completed by remember {
        mutableStateOf<Boolean>(false)
    }

    val animWidth by animateFloatAsState(
        targetValue = if (completed) 200f else 350f, label = "anim_val_image_width",
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutBack),
            repeatMode = RepeatMode.Restart,
        ), label = "anim_val_image_rotate_angle"
    )

    SubcomposeAsyncImage(
        modifier = modifier,
        model = pokemonDetail.imageSrc,
        contentDescription = "image",
        imageLoader = imageLoader,
        alignment = Alignment.Center,
        onSuccess = {
            completed = true
        },
        success = {
            Image(
                painter = it.painter,
                contentDescription = "Loading",
                modifier = Modifier
                    .width(animWidth.dp)
                    .height(animWidth.dp)
                    .zIndex(1f)
            )
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.ic_pokedex_logo),
                contentDescription = "Loading",
                modifier = Modifier
                    .size(180.dp),
                alpha = 0.3f
            )
        },
        loading = {
            Image(
                painter = painterResource(id = R.drawable.ic_pokedex_logo),
                contentDescription = "Loading",
                modifier = Modifier
                    .size(180.dp)
                    .rotate(angle)
            )
        }
    )

}


@Composable
fun ContentCardView(modifier: Modifier, navHeight: Dp, pokemonDetail: PokemonDetail) {
    val verticalScrollState = rememberScrollState()

    Box(
        modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = navHeight / 2)
            .verticalScroll(state = verticalScrollState)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PokemonTypeView(types = pokemonDetail.types)
            HeadTitleView("About", color = pokemonDetail.types.first().color)
            PokemonPhysicsView(pokemonDetail = pokemonDetail)
            PokemonDescriptionView(pokemonDetail.description)
            HeadTitleView("Base Stats", color = pokemonDetail.types.first().color)
            PokemonStatsView(pokemonDetail = pokemonDetail)
        }
    }
}

@Composable
fun PokemonStatsView(pokemonDetail: PokemonDetail) {
    val color = pokemonDetail.types.get(0).color
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        for (stat in pokemonDetail.statsList) {
            key(pokemonDetail.index) {
                PokemonStatItem(stat, color)
            }
        }
    }
}

@Stable
@Composable
fun PokemonStatItem(stats: Stats, color: Color, maxStat: Int = 250) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(stats) {
        animatedProgress.snapTo(0f)  // 즉시 0으로 설정
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 700,
                easing = EaseInOut,
                delayMillis = 350
            )
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stats.toPrettyName(),
            modifier = Modifier
                .width(40.dp)
                .padding(end = 8.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleSmall.copy(
                color = color, fontWeight = FontWeight.Bold
            )
        )
        Box(
            modifier = Modifier
                .height(22.dp)
                .width(1.dp)
                .background(color = Grayscale.Light.color)
                .padding(12.dp)
        )
        Text(
            stats.value.toString().toPokedexIndex(),
            modifier = Modifier.width(36.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(
                    color = color.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(percent = 100)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((stats.value.div(maxStat.toFloat())) * animatedProgress.value)
                    .height(4.dp)
                    .background(color = color, shape = RoundedCornerShape(percent = 100))
            )
        }
    }
}
