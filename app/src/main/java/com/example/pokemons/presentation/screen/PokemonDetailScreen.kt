package com.example.pokemons.presentation.screen

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
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
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.pokemons.R
import com.example.pokemons.ui.theme.PokemonTheme

@Composable
fun PokemonDetailScreen() {
    PokemonTheme {
        PokemonDetailView()
    }
}

@Composable
fun PokemonDetailView() {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Green)
        ) {
            val navHeight = remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val guideline = createGuidelineFromTop(0.3f)

                val (backgroundFieldViewRef, navViewRef, contentViewRef) = createRefs()

                BackgroundFieldView(Modifier.constrainAs(backgroundFieldViewRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideline)

                    height = Dimension.fillToConstraints
                })
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
                        .zIndex(2f)
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
                        }, navHeight = navHeight.value
                )
            }
            Header()
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
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
fun BackgroundFieldView(modifier: Modifier) {
    Box(
        modifier = modifier
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
fun PokemonImageView(modifier: Modifier = Modifier) {
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
fun PokemonNavigationView(modifier: Modifier) {
    Row(
        modifier = modifier.padding(vertical = 2.dp)
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
fun ContentCardView(modifier: Modifier, navHeight: Dp) {
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
            PokemonPhysicsView()
            PokemonDescriptionView()
        }
    }
}

@Composable
fun PokemonPhysicsView(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
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