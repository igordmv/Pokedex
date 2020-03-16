package com.idv.pokemondetails.view.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonAbilityDetailsViewModel
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.TypedPokemonsViewModel
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonDetailsPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Boolean>>()
    private val pokemonDetailsObserver = mock<Observer<PokemonDetailsViewModel>>()
    private val abilityDetailsObserver = mock<Observer<PokemonAbilityDetailsViewModel>>()
    private val typedPokemonsObserver = mock<Observer<List<TypedPokemonsViewModel>>>()

    private val mapper = mock<PokemonDetailsMapper>()
    private val subject = PokemonDetailsPresenterImpl(mapper)
    private val pokemon = Pokemon("name", "id", "image")
    private val pokemonAbility = PokemonAbility("title", "effect", "showEffect")
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


    @Before
    fun before() {
        subject.getLoadingObservable().observeForever(loadingObserver)
        subject.getErrorObservable().observeForever(errorObserver)
        subject.getAbilityDetailsObservable().observeForever(abilityDetailsObserver)
        subject.getPokemonDetailsObservable().observeForever(pokemonDetailsObserver)
        subject.getTypedPokemonsObservable().observeForever(typedPokemonsObserver)
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
    fun `when call presenter pokemonDetails expect post value to observable`() {
        val anyDetailsViewModel = any<PokemonDetailsViewModel>()
        whenever(mapper.mapPokemonDetails(pokemonDetails)).doReturn(anyDetailsViewModel)
        subject.presentPokemonDetails(pokemonDetails)
        verify(pokemonDetailsObserver).onChanged(anyDetailsViewModel)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(false)
    }

    @Test
    fun `when call presenter abilityDetails expect post value to observable`() {
        val anyAbility = any<PokemonAbilityDetailsViewModel>()
        whenever(mapper.mapAbilities(pokemonAbility)).doReturn(anyAbility)
        subject.presentAbilityDetails(pokemonAbility)
        verify(abilityDetailsObserver).onChanged(anyAbility)
        verify(loadingObserver).onChanged(false)
    }
}