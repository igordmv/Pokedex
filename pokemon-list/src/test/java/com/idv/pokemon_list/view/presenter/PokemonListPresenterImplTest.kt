package com.idv.pokemon_list.view.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_list.view.mapper.MainMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonListPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Boolean>>()
    private val pokemonObserver = mock<Observer<List<PokemonViewModel>>>()
    private val mapper = mock<MainMapper>()
    private val subject = PokemonListPresenterImpl(mapper)
    private val pokemon = Pokemon("name", "id", "image")

    @Before
    fun before() {
        subject.getLoadingObservable().observeForever(loadingObserver)
        subject.getErrorObservable().observeForever(errorObserver)
        subject.getPokemonObservable().observeForever(pokemonObserver)
    }

    @Test
    fun `when call presenter error expect post false to observable`() {
        subject.presentError()
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(true)
    }

    @Test
    fun `when call presenter loading passing true expect post true to observable`() {
        subject.presentLoadingState(true)
        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged(false)
    }

    @Test
    fun `when call presenter loading passing false expect post false to observable`() {
        subject.presentLoadingState(false)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
    }

    @Test
    fun `when call presentPokemon expect post value on observer`(){
        val mappedPokemon = any<PokemonViewModel>()
        whenever(mapper.mapPokemon(pokemon)).doReturn(mappedPokemon)
        subject.presentPokemon(pokemon)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
        verify(pokemonObserver).onChanged(listOf(mappedPokemon))
    }

    @Test
    fun `when call present pokemons expect post value on observer` () {
        val mappedPokemons = listOf<PokemonViewModel>()
        whenever(mapper.mapPokemons(listOf(pokemon))).doReturn(mappedPokemons)
        subject.presentPokemons(listOf(pokemon))
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
        verify(pokemonObserver).onChanged(mappedPokemons)
    }
}