package com.idv.pokedex.view.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.idv.pokedex.view.mapper.MainMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MainPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Boolean>>()
    private val pokemonObserver = mock<Observer<PokemonViewModel>>()
    private val mapper = mock<MainMapper>()
    private val subject = MainPresenterImpl(mapper)

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
}