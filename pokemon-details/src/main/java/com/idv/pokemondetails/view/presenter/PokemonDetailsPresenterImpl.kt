package com.idv.pokemondetails.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper

internal class PokemonDetailsPresenterImpl(private val mapper: PokemonDetailsMapper) : PokemonDetailsPresenter {

    private val loadingObserver = MutableLiveData<Boolean>()
    private val errorObserver = MutableLiveData<Boolean>()
    private val pokemonDetailsObserver = MutableLiveData<PokemonDetailsViewModel>()

    override fun presentPokemonDetails(pokemonDetails: PokemonDetails) {
        val mappedDetails = mapper.mapPokemonDetails(pokemonDetails)
        pokemonDetailsObserver.postValue(mappedDetails)
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
    }

    override fun presentError() {
        loadingObserver.postValue(false)
        errorObserver.postValue(true)
    }

    override fun presentLoadingState(showLoading: Boolean) {
        loadingObserver.postValue(showLoading)
        errorObserver.postValue(false)
    }

    override fun getErrorObservable(): MutableLiveData<Boolean> = errorObserver
    override fun getLoadingObservable(): MutableLiveData<Boolean> = loadingObserver
    override fun getPokemonDetailsObservable(): MutableLiveData<PokemonDetailsViewModel> = pokemonDetailsObserver

}