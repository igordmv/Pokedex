package com.idv.pokemon_list.view.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_list.view.mapper.PokemonListMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonListPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Boolean>>()
    private val paginateLoadingObserver = mock<Observer<Boolean>>()
    private val pokemonsObserver = mock<Observer<List<PokemonViewModel>>>()
    private val pokemonObserver = mock<Observer<PokemonViewModel>>()
    private val paginatePokemonObserver = mock<Observer<List<PokemonViewModel>>>()
    private val mapper = mock<PokemonListMapper>()
    private val subject = PokemonListPresenterImpl(mapper)
    private val pokemon = Pokemon("name", "id", "image")

    @Before
    fun before() {
        subject.getLoadingObservable().observeForever(loadingObserver)
        subject.getPaginateLoadingObservable().observeForever(paginateLoadingObserver)
        subject.getErrorObservable().observeForever(errorObserver)
        subject.getPokemonsObservable().observeForever(pokemonsObserver)
        subject.getPokemonObservable().observeForever(pokemonObserver)
        subject.getPaginatedPokemonsObservable().observeForever(paginatePokemonObserver)
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
        verify(pokemonObserver).onChanged(mappedPokemon)
    }

    @Test
    fun `when call present pokemons expect post value on observer` () {
        val mappedPokemons = listOf<PokemonViewModel>()
        whenever(mapper.mapPokemons(listOf(pokemon))).doReturn(mappedPokemons)
        subject.presentPokemons(listOf(pokemon))
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
        verify(pokemonsObserver).onChanged(mappedPokemons)
    }

    @Test
    fun `when call present paginted pokemons expect post value on observer` () {
        val mappedPokemons = listOf<PokemonViewModel>()
        whenever(mapper.mapPokemons(listOf(pokemon))).doReturn(mappedPokemons)
        subject.presentPaginatedPokemons(listOf(pokemon))
        verify(paginateLoadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
        verify(paginatePokemonObserver).onChanged(mappedPokemons)
    }

    @Test
    fun `when call presenter paginate loading passing true expect post true to observable`() {
        subject.presentPaginateLoadingState(true)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
    }
}