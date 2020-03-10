package com.idv.pokedex.view.presenter

import androidx.lifecycle.MutableLiveData

internal class MainPresenterImpl : MainPresenter {
    private val showPokemonListObserver = MutableLiveData<Boolean>()

    override fun showPokemonList() {
        showPokemonListObserver.postValue(true)
    }

    override fun getShowPokemonListObservable(): MutableLiveData<Boolean> = showPokemonListObserver
}