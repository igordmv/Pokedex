package com.idv.pokemondetails.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper

internal interface PokemonDetailsPresenter {

    fun presentError()

    fun presentLoadingState(showLoading : Boolean)

    fun presentPokemonDetails(pokemonDetails: PokemonDetails)

    fun getErrorObservable(): MutableLiveData<Boolean>

    fun getLoadingObservable(): MutableLiveData<Boolean>

    fun getPokemonDetailsObservable(): MutableLiveData<PokemonDetailsViewModel>

    companion object Factory {
        fun make() : PokemonDetailsPresenter {
            val mapper = PokemonDetailsMapper.make()
            return PokemonDetailsPresenterImpl(mapper)
        }
    }
}