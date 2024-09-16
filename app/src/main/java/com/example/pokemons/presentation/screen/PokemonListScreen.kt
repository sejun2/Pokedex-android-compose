package com.example.pokemons.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.pokemons.R
import com.example.pokemons.domain.model.PokemonSummary
import com.example.pokemons.presentation.viewmodel.PokemonListUiState
import com.example.pokemons.presentation.viewmodel.PokemonSummaryListViewModel
import com.example.pokemons.ui.theme.PokemonTheme
import com.example.pokemons.ui.theme.Primary
import com.example.pokemons.util.capitalizeFirstLowercaseRest
import com.example.pokemons.util.toPokedexIndex
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun PokemonListScreen(
    viewModel: PokemonSummaryListViewModel = hiltViewModel(),
    navigateToPokemonDetail: (Int) -> Unit
) {
    PokemonTheme {
        Scaffold { innerInsets ->
            Box(Modifier.padding(innerInsets)) {
                PokemonListView(viewModel, navigateToPokemonDetail)
            }
        }
    }
}

@Stable
@Composable
fun PokemonCard(
    pokemon: PokemonSummary,
    imageLoader: ImageLoader = LocalContext.current.imageLoader,
    onClick: () -> Unit
) {
    val localContext = LocalContext.current

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.8f)
    ) {
        // Background card with shadow and border
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onClick()
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                // Pokemon number
                Text(
                    text = "#${pokemon.index.toString().toPokedexIndex()}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(bottom = 4.dp, top = 4.dp, end = 4.dp)
                )

                // Pokemon image
                AsyncImage(
                    model =
                    ImageRequest.Builder(context = localContext).data(
                        pokemon.imageSrc
                    ).crossfade(true).size(120).build(),
                    contentDescription = "Pokemon image",
                    imageLoader = imageLoader,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(120.dp)
                        .zIndex(1f)
                )

                // Pokemon name
                Surface(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = pokemon.name.capitalizeFirstLowercaseRest(),
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun PokemonListHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Primary)
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(28.dp)
                .padding(end = 6.dp),
            painter = painterResource(id = R.drawable.ic_pokedex_logo),
            contentDescription = "logo_icon",
        )
        Text(
            text = "Pokédex",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
        )
    }
}

@Composable
fun PokedexSearchBar(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp, vertical = 12.dp
            ),
        placeholder = { Text("Input pokemon name") },
        leadingIcon = {
            Image(
                painterResource(R.drawable.ic_search),
                contentDescription = "ic_search"
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(
            100
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
        )
    )
}

@Composable
fun PokemonListView(
    pokemonSummaryListViewModel: PokemonSummaryListViewModel = hiltViewModel(),
    navigateToPokemonDetail: (Int) -> Unit
) {
    val uiState by pokemonSummaryListViewModel.uiState.collectAsState()
    val lazyGridState = rememberSaveable(saver = LazyGridState.Saver) {
        LazyGridState()
    }

    var searchValue by rememberSaveable {
        mutableStateOf<String>("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        PokemonListHeader()
        PokedexSearchBar(searchValue) {
            searchValue = it
            pokemonSummaryListViewModel.search(searchValue)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        12.dp
                    ),
                )
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("tag_circular_progress_indicator")
                )

                is PokemonListUiState.Error -> Text(
                    "Error: $state",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center),
                )

                is PokemonListUiState.Success -> {
                    PokemonList(
                        pokemonList = state.pokemonList,
                        lazyGridState = lazyGridState,
                        onLoadMore = { pokemonSummaryListViewModel.loadMore() },
                        onItemClick = navigateToPokemonDetail,
                        isSearchMode = pokemonSummaryListViewModel.searchValue.collectAsState().value.isNotEmpty()
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<PokemonSummary>,
    lazyGridState: LazyGridState,
    onLoadMore: () -> Unit,
    onItemClick: (Int) -> Unit,
    isSearchMode: Boolean
) {
    LazyVerticalGrid(
        columns = Fixed(3),
        state = lazyGridState
    ) {
        items(pokemonList, key = { it.index }) { pokemon ->
            PokemonCard(pokemon) {
                onItemClick(pokemon.index)
            }
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index == lazyGridState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if(isSearchMode) return@LaunchedEffect

        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}

@Preview(showBackground = true, widthDp = 130)
@Composable
fun PreviewPokemonCard() {
    PokemonCard(pokemon = PokemonSummary("TestName", "TestUrl", 1)) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPokemonListScreen() {
    PokemonListScreen {}
}