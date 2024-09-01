package com.example.pokemons.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.pokemons.R
import com.example.pokemons.domain.model.PokemonSummary
import com.example.pokemons.presentation.viewmodel.PokemonListUiState
import com.example.pokemons.presentation.viewmodel.PokemonSummaryListViewModel
import com.example.pokemons.ui.theme.Primary
import com.example.pokemons.ui.theme.PokemonTheme

@Composable
fun PokemonListScreen() {
    PokemonTheme {
        PokemonListView()
    }
}

@Composable
fun PokemonCard(pokemon: PokemonSummary) {
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
                .background(Color.White),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                // Pokemon number
                Text(
                    text = "#${pokemon.index}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(bottom = 4.dp, top = 4.dp, end = 4.dp)
                )

                // Pokemon image
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.index}.png",
                    contentDescription = "Pokemon image",
                    imageLoader = ImageLoader(LocalContext.current),
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
                            text = pokemon.name,
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexSearchBar() {
    SearchBar(query = "", onQueryChange = {}, onSearch = {

    }, active = true, onActiveChange = {}) {

    }
}

@Composable
fun PokemonListView(pokemonSummaryListViewModel: PokemonSummaryListViewModel = hiltViewModel()) {
    val uiState by pokemonSummaryListViewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        pokemonSummaryListViewModel.fetchPokemonList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        PokemonListHeader()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
                .background(
                    color = Color.White, shape = RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp
                    )
                )
                .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                is PokemonListUiState.Error -> Text(
                    "Error: ${state}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center),
                    )

                is PokemonListUiState.Success -> {
                    PokemonList(
                        pokemonList = state.pokemonList,
                        lazyGridState = lazyGridState,
                        onLoadMore = { pokemonSummaryListViewModel.loadMore() }
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
    onLoadMore: () -> Unit
) {
    LazyVerticalGrid(
        columns = Fixed(
            count = 3,
        ),
        state = lazyGridState
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon)
        }
        item {
            LaunchedEffect(Unit) {
                onLoadMore()
            }
        }
    }

    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex == pokemonList.size - 5) {
                    onLoadMore()
                }
            }
    }
}

@Preview(showBackground = true, widthDp = 130)
@Composable
fun PreviewPokemonCard() {
    PokemonCard(pokemon = PokemonSummary("TestName", "TestUrl", 1))
}
