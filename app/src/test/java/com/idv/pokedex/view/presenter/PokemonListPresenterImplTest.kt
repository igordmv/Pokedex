package com.idv.pokedex.view.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonListPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val showPokemonListObserver = mock<Observer<Boolean>>()
    private val subject = MainPresenterImpl()

    @Before
    fun before() {
        subject.getShowPokemonListObservable().observeForever(showPokemonListObserver)
    }

    @Test
    fun `assert observer is being changed`(){
        subject.showPokemonList()
        verify(showPokemonListObserver).onChanged(true)
    }

}