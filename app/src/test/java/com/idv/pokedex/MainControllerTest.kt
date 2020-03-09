package com.idv.pokedex

import com.idv.pokedex.view.presenter.MainPresenter
import com.idv.pokemon.usecases.get.PokemonGetter
import com.idv.pokemon_entity.Pokemon
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

internal class MainControllerTest  {

    private val pokemonGetter = mock<PokemonGetter>()
    private val presenter = mock<MainPresenter>()
    private val subject = MainController(pokemonGetter, presenter)
    private val pokemon = Pokemon("name")

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
}