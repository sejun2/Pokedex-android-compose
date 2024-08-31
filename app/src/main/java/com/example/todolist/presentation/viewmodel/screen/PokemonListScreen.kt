package com.example.todolist.presentation.viewmodel.screen

import android.provider.CalendarContract.Colors
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.R
import com.example.todolist.domain.model.PokemonSummary
import com.example.todolist.presentation.viewmodel.PokemonListUiState
import com.example.todolist.presentation.viewmodel.PokemonSummaryListViewModel
import com.example.todolist.ui.theme.TodolistTheme

@Composable
fun PokemonListScreen() {
    TodolistTheme {
        PokemonListView()
    }
}

@Composable
fun PokemonListItem(pokemonData: PokemonSummary) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(color = Color.White)
            .border(
                color = Color.Gray,
                width = 2.dp,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        Text(text = "${pokemonData.name}\n${pokemonData.url}")
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
            // Pokemon number
            Text(
                text = "#${pokemon.name}",
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 4.dp, top = 4.dp, end = 4.dp)
            )
            // Pokemon image
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),  // 실제 이미지 리소스로 변경 필요
                contentDescription = "Pokemon ${pokemon.name}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = 20.dp)  // 이미지를 약간 아래로 이동
                    .zIndex(1f)
            )

            // Pokemon name
            Surface(
                color = Color.LightGray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
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
            .padding(16.dp)
    ) {
        Text(
            text = "Pokemon List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = uiState) {
            is PokemonListUiState.Loading -> CircularProgressIndicator()
            is PokemonListUiState.Error -> Text(
                "Error: ${state.toString()}",
                color = MaterialTheme.colorScheme.error
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
    PokemonCard(pokemon = PokemonSummary("TestName", "TestUrl"))
}
