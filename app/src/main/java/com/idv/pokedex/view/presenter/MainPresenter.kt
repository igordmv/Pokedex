package com.idv.pokedex.view.presenter

import androidx.lifecycle.MutableLiveData

internal interface MainPresenter {

    fun showPokemonList()

    fun getShowPokemonListObservable(): MutableLiveData<Boolean>

    companion object Factory {
        fun make () : MainPresenter {
            return MainPresenterImpl()
        }
    }
}