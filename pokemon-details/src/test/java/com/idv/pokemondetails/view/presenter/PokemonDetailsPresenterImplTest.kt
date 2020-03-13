package com.idv.pokemondetails.view.presenter

import androidx.lifecycle.Observer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PokemonDetailsPresenterImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Boolean>>()
    private val mapper = mock<PokemonDetailsMapper>()
    private val subject = PokemonDetailsPresenterImpl(mapper)

    @Before
    fun before() {
        subject.getLoadingObservable().observeForever(loadingObserver)
        subject.getErrorObservable().observeForever(errorObserver)
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

}