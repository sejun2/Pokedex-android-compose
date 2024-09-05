package com.example.pokemons.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.EventListener
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.DataSource
import coil.disk.DiskCache
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokemons.R
import com.example.pokemons.domain.model.PokemonDetail
import com.example.pokemons.presentation.viewmodel.PokemonDetailUiState
import com.example.pokemons.presentation.viewmodel.PokemonDetailViewModel
import com.example.pokemons.presentation.widget.PokemonType
import com.example.pokemons.ui.theme.PokemonTheme
import com.example.pokemons.ui.theme.Typography

@Composable
fun PokemonDetailScreen(pokemonId: Int, onNavigateUp: () -> Boolean) {
    PokemonTheme {
        PokemonDetailView(onNavigateUp, pokemonId)
    }
}

@Composable
fun PokemonDetailView(
    onNavigateUp: () -> Boolean,
    pokemonId: Int,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel<PokemonDetailViewModel>()
) {
    LaunchedEffect(Unit) {
        pokemonDetailViewModel.fetchPokemonDetail(pokemonIndex = pokemonId)
    }

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

            when (val state = uiState.value) {
                is PokemonDetailUiState.Success ->
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = state.data.types[0].color)
                    ) {
                        val guideline = createGuidelineFromTop(0.3f)

                        val (backgroundFieldViewRef, navViewRef, contentViewRef) = createRefs()

                        BackgroundFieldView(Modifier.constrainAs(backgroundFieldViewRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(guideline)

                            height = Dimension.fillToConstraints
                        }, state.data.types[0])

                        PokemonNavigationView(
                            Modifier
                                .constrainAs(navViewRef) {
                                    bottom.linkTo(guideline)
                                    top.linkTo(guideline)

                                    width = Dimension.matchParent
                                }
                                .padding(bottom = 36.dp)
                                .onGloballyPositioned { coordinates ->
                                    navHeight.value = with(density) {
                                        coordinates.size.height.toDp()
                                    }
                                }
                                .zIndex(2f),
                            state.data.index
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
                                }, navHeight = navHeight.value,
                            pokemonDetail = state.data
                        )
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
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState = pokemonDetailViewModel.uiState.collectAsState()
    Row(
        modifier = Modifier.padding(end = 12.dp, start = 4.dp, top = 8.dp, bottom = 8.dp),
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
                    state.data.name, style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.W900,
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "#${state.data.index}", style = TextStyle(
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

@Composable
fun BackgroundFieldView(modifier: Modifier, type: PokemonType) {
    Box(
        modifier = modifier
            .background(color = type.color)
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokedex_logo),
            contentDescription = "logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(240.dp),
            alpha = 0.48f
        )
    }
}

@Composable
fun PokemonImageView(modifier: Modifier = Modifier, pokemonId: Int) {
    val localContext = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context = localContext)
            .diskCache {
                DiskCache.Builder().directory(
                    directory = localContext.filesDir
                ).build()
            }
            .build()
    }

    AsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/$pokemonId.png",
        contentDescription = "image",
        imageLoader = imageLoader,
        placeholder = painterResource(id = R.drawable.ic_pokedex_logo),
        alignment = Alignment.Center,
        modifier = Modifier
            .width(180.dp)
            .height(180.dp)
            .zIndex(1f)
    )
}

@Composable
fun PokemonNavigationView(modifier: Modifier, pokemonId: Int) {
    Row(
        modifier = modifier
            .padding(vertical = 2.dp)
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "left_arrow",
            tint = White,
            modifier = Modifier
                .width(50.dp)
        )
        PokemonImageView(pokemonId = pokemonId)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "right_arrow",
            tint = White,
            modifier = Modifier
                .width(50.dp)
        )
    }
}

@Composable
fun ContentCardView(modifier: Modifier, navHeight: Dp, pokemonDetail: PokemonDetail) {
    Box(
        modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = White)
            .padding(top = navHeight / 2)
    ) {
        Column {
            PokemonPhysicsView(pokemonDetail = pokemonDetail)
            PokemonDescriptionView()
        }
    }
}

@Composable
fun PokemonPhysicsView(modifier: Modifier = Modifier, pokemonDetail: PokemonDetail) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_weight),
                    contentDescription = "image_weight"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("${pokemonDetail.weight / 10} kg")
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("Weight")
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_straighten),
                    contentDescription = "image_height"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("${(pokemonDetail.height / 10)} m")
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("Height")
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text("${pokemonDetail.moves}")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Moves")
        }
    }
}

@Composable
fun PokemonDescriptionView() {
    Text(
        "There is a plant seed on tis back right from the day this Pokemon is born. The seed slowly grows larger.",
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPokemonDetailView() {
}