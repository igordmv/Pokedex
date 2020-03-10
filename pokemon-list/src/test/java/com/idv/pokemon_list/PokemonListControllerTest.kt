package com.idv.pokemon_list

import com.idv.pokemon.usecases.get.PokemonGetter
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_list.view.presenter.PokemonListPresenter
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

internal class PokemonListControllerTest  {

    private val pokemonGetter = mock<PokemonGetter>()
    private val presenter = mock<PokemonListPresenter>()
    private val subject =
        PokemonListController(pokemonGetter, presenter)
    private val pokemon = Pokemon("name", "id", "image")

    @Test
    fun `test when getPokemon works expect call presentPokemon`() = runBlocking {
        whenever(pokemonGetter.getPokemon("pokemon")).doReturn(pokemon)
        subject.getPokemon("pokemon").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentPokemon(pokemon)
    }

    @Test
    fun `test when getPokemon gives IOException expect call presentError`() = runBlocking {
        whenever(pokemonGetter.getPokemon("pokemon")).doThrow(IOException())
        subject.getPokemon("pokemon").join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentError()
    }

    @Test
    fun `test when getPokemons works expect call presentPokemon`() = runBlocking {
        whenever(pokemonGetter.getPokemons()).doReturn(listOf(pokemon))
        subject.getFirstPokemons().join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentPokemons(listOf(pokemon))
    }

    @Test
    fun `test when getPokemons gives IOException expect call presentError`() = runBlocking {
        whenever(pokemonGetter.getPokemons()).doThrow(IOException())
        subject.getFirstPokemons().join()
        verify(presenter).presentLoadingState(true)
        verify(presenter).presentError()
    }
}