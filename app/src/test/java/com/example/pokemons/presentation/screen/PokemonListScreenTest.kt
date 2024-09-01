package com.example.pokemons.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokemons.core.di.AppModule
import com.example.pokemons.domain.model.PokemonSummary
import com.example.pokemons.presentation.viewmodel.PokemonListUiState
import com.example.pokemons.presentation.viewmodel.PokemonSummaryListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@HiltAndroidTest
@UninstallModules(
    AppModule::class
)
@Config(application = HiltTestApplication::class, sdk = [33])
@RunWith(AndroidJUnit4::class)
class PokemonListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: PokemonSummaryListViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mockk(relaxed = true)
    }

    @Test
    fun whenPokemonListLoaded_shouldDisplayPokemonNames() {
        // Given
        val pokemonList = listOf(
            PokemonSummary("Bulbasaur", "url", 1),
            PokemonSummary("Charmander", "url", 4),
            PokemonSummary("Squirtle", "url", 7)
        )
        every { viewModel.uiState } returns MutableStateFlow(PokemonListUiState.Success(pokemonList))

        // When
        composeTestRule.setContent {
            PokemonListView(viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("Charmander").assertIsDisplayed()
        composeTestRule.onNodeWithText("Squirtle").assertIsDisplayed()
    }

    @Test
    fun whenPokemonListLoaded_shouldDisplayHeaderWithPokedexIcon() {
        // Given
        val pokemonList = listOf(
            PokemonSummary("Bulbasaur", "url", 1),
            PokemonSummary("Charmander", "url", 4),
            PokemonSummary("Squirtle", "url", 7)
        )
        every { viewModel.uiState } returns MutableStateFlow(PokemonListUiState.Success(pokemonList))

        // When
        composeTestRule.setContent {
            PokemonListView(viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("logo_icon").assertIsDisplayed()
    }
}