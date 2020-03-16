package com.idv.pokemondetails

import com.idv.pokemon.usecases.get.PokemonGetter
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.presenter.PokemonDetailsPresenter
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

internal class PokemonDetailsControllerTest {

    private val pokemonGetter = mock<PokemonGetter>()
    private val presenter = mock<PokemonDetailsPresenter>()
    private val subject =
        PokemonDetailsController(pokemonGetter, presenter)
    private val pokemon = Pokemon("name", "id", "image")
    private val pokemonDetails = PokemonDetails(
        1,
        "name",
        1,
        1,
        "defaultImage",
        emptyList(),
        emptyList(),
        1,
        1,
        1,
        1,
        1,
        1,
        emptyList(),
        emptyList()
    )

    private val pokemonAbility = PokemonAbility("title", "effect", "showEffect")

    @Test
    fun `test when getPokemonDetails works expect call presentPokemonDetails`() = runBlocking {
        whenever(pokemonGetter.getPokemonDetails("pokemon")).doReturn(pokemonDetails)
        subject.getPokemon("pokemon").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentPokemonDetails(pokemonDetails)
    }

    @Test
    fun `test when getPokemonDetails gives IOException expect call presentError`() = runBlocking {
        whenever(pokemonGetter.getPokemonDetails("pokemon")).doThrow(IOException())
        subject.getPokemon("pokemon").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentError()
    }

    @Test
    fun `test when getAbilityDetails works expect call presentAbilityDetails`() = runBlocking {
        whenever(pokemonGetter.getAbilityDetails("ability")).doReturn(pokemonAbility)
        subject.getAbilityDetails("ability").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentAbilityDetails(pokemonAbility)
    }

    @Test
    fun `test when getAbilityDetails gives IOException expect call presentError`() = runBlocking {
        whenever(pokemonGetter.getAbilityDetails("ability")).doThrow(IOException())
        subject.getAbilityDetails("ability").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentError()
    }

    @Test
    fun `test when getPokemonsByType works expect call presentTypePokemons`() = runBlocking {
        whenever(pokemonGetter.getPokemonsByType("type")).doReturn(listOf(pokemon))
        subject.getPokemonsByType("type").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentTypePokemons(listOf(pokemon), "type")
    }

    @Test
    fun `test when getPokemonsByType gives IOException expect call presentError`() = runBlocking {
        whenever(pokemonGetter.getPokemonsByType("type")).doThrow(IOException())
        subject.getPokemonsByType("type").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentError()
    }
}