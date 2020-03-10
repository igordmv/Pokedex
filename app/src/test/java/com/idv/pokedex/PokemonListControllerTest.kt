package com.idv.pokedex

import com.idv.pokedex.view.presenter.MainPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PokemonListControllerTest {

    private val presenter = mock<MainPresenter>()
    private val subject = MainController(presenter)

    @Test
    fun `Assert controller is calling presenter show pokemon list`() = runBlocking {
        subject.onViewCreated().join()
        verify(presenter).showPokemonList()
    }

}