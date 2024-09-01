package com.example.todolist.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.todolist.R
import com.example.todolist.presentation.widget.PokemonType
import com.example.todolist.presentation.widget.TypeChip
import com.example.todolist.ui.theme.PokemonTheme

@Composable
fun PokemonDetailScreen() {
    PokemonTheme {
        PokemonDetailView()
    }
}

@Composable
fun PokemonDetailView() {
    val scrollState = rememberScrollState()

    Scaffold(
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = Green)
                .fillMaxSize()
        ) {
            BackgroundFieldView()
            Box(
                modifier = Modifier
                    .offset(y = 100.dp)
                    .zIndex(1f)
            ) {
                PokemonNavigationView()
            }
            Column(
                Modifier
                    .padding(start = 3.dp, end = 3.dp, bottom = 3.dp)
                    .align(Alignment.BottomCenter)
                    .height(550.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 12.dp, topEnd = 12.dp,
                            bottomStart = 12.dp, bottomEnd = 12.dp,
                        )
                    )
                    .shadow((6).dp)
                    .verticalScroll(scrollState)
                    .background(color = White)
            ) {
                // prevent from overlap with PokemonNavigationView
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TypeChip(
                        PokemonType.BUG
                    )
                    TypeChip(
                        PokemonType.FIRE
                    )
                    TypeChip(
                        PokemonType.DRAGON
                    )
                }
                Text(
                    text = "About",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                PokemonPhysicsView()
                PokemonDescriptionView()
            }
            Header()
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.padding(end = 12.dp, start = 4.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = White,
            contentDescription = "back_button",
            modifier = Modifier
                .padding(12.dp)

        )
        Text(
            "PokemonName", style = TextStyle(
                color = White,
                fontWeight = FontWeight.W900,
                fontSize = 18.sp,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "#001", style = TextStyle(
                color = White,
                fontWeight = FontWeight.W900,
                fontSize = 12.sp
            )
        )
    }

}

@Composable
fun BackgroundFieldView() {
    Box(
        modifier = Modifier
            .background(color = Green)
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
fun PokemonImageView() {
    AsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/1.png",
        contentDescription = "image",
        imageLoader = ImageLoader(context = LocalContext.current),
        placeholder = painterResource(id = R.drawable.ic_pokedex_logo),
        alignment = Alignment.Center,
        modifier = Modifier
            .width(180.dp)
            .height(180.dp)
            .zIndex(1f)
    )
}

@Composable
fun PokemonNavigationView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
        PokemonImageView()
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
fun ContentView() {

}

@Composable
fun PokemonPhysicsView() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_weight),
                    contentDescription = "image_weight"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("6.9 kg")
            }
            Text("weight")
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_weight),
                    contentDescription = "image_weight"
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("6.9 kg")
            }
            Text("weight")
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("6.9 kg")
            Text("weight")
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
    PokemonDetailView()
}