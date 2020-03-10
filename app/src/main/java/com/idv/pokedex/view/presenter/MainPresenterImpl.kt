package com.idv.pokedex.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokedex.view.mapper.MainMapper
import com.idv.pokemon_entity.Pokemon

internal class MainPresenterImpl(private val mapper : MainMapper) : MainPresenter {
    private val loadingObserver = MutableLiveData<Boolean>()
    private val errorObserver = MutableLiveData<Boolean>()
    private val pokemonsObserver = MutableLiveData<PokemonsViewModel>()

    override fun presentPokemon(pokemon: Pokemon) {
        val mappedPokemon = mapper.mapPokemon(pokemon)
        pokemonsObserver.postValue(mappedPokemon)
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

    override fun presentPokemons(pokemons: List<Pokemon>) {
        val mappedPokemons = mapper.mapPokemons(pokemons)
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
        pokemonsObserver.postValue(mappedPokemons)
    }

    override fun getErrorObservable(): MutableLiveData<Boolean> = errorObserver
    override fun getLoadingObservable(): MutableLiveData<Boolean> = loadingObserver
    override fun getPokemonObservable(): MutableLiveData<PokemonsViewModel> = pokemonsObserver



}