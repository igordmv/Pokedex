package com.idv.pokemon_list.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_list.view.mapper.MainMapper
import com.idv.pokemon_entity.Pokemon

internal class PokemonListPresenterImpl(private val mapper : MainMapper) : PokemonListPresenter {
    private val loadingObserver = MutableLiveData<Boolean>()
    private val paginateLoadingObserver = MutableLiveData<Boolean>()
    private val errorObserver = MutableLiveData<Boolean>()
    private val pokemonsObserver = MutableLiveData<List<PokemonViewModel>>()
    private val paginatedPokemonsObserver = MutableLiveData<List<PokemonViewModel>>()

    override fun presentPokemon(pokemon: Pokemon) {
        val mappedPokemon = mapper.mapPokemon(pokemon)
        pokemonsObserver.postValue(listOf(mappedPokemon))
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
    }

    override fun presentError() {
        loadingObserver.postValue(false)
        errorObserver.postValue(true)
     }

    override fun presentPaginateLoadingState(showLoading: Boolean) {
        paginateLoadingObserver.postValue(showLoading)
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
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

    override fun presentPaginatedPokemons(pokemons: List<Pokemon>) {
        val mappedPokemons = mapper.mapPokemons(pokemons)
        paginateLoadingObserver.postValue(false)
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
        paginatedPokemonsObserver.postValue(mappedPokemons)
    }

    override fun getErrorObservable(): MutableLiveData<Boolean> = errorObserver
    override fun getLoadingObservable(): MutableLiveData<Boolean> = loadingObserver
    override fun getPaginateLoadingObservable(): MutableLiveData<Boolean> = paginateLoadingObserver
    override fun getPokemonObservable(): MutableLiveData<List<PokemonViewModel>> = pokemonsObserver
    override fun getPaginatedPokemonsObservable(): MutableLiveData<List<PokemonViewModel>> = paginatedPokemonsObserver



}