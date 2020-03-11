package com.idv.pokemon_list.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_list.view.mapper.PokemonListMapper
import com.idv.pokemon_entity.Pokemon

internal class PokemonListPresenterImpl(private val mapper : PokemonListMapper) : PokemonListPresenter {
    private val loadingObserver = MutableLiveData<Boolean>()
    private val paginateLoadingObserver = MutableLiveData<Boolean>()
    private val errorObserver = MutableLiveData<Boolean>()
    private val pokemonsObserver = MutableLiveData<List<PokemonViewModel>>()
    private val pokemonObserver = MutableLiveData<PokemonViewModel>()
    private val paginatedPokemonsObserver = MutableLiveData<List<PokemonViewModel>>()

    override fun presentPokemon(pokemon: Pokemon?) {
        pokemon?.let { pokemon ->
            val mappedPokemon = mapper.mapPokemon(pokemon)
            pokemonObserver.postValue(mappedPokemon)
            loadingObserver.postValue(false)
            errorObserver.postValue(false)
        }?:run {
            pokemonObserver.postValue(null)
            loadingObserver.postValue(false)
            errorObserver.postValue(false)
        }
    }

    override fun presentError() {
        loadingObserver.postValue(false)
        paginateLoadingObserver.postValue(false)
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
    override fun getPokemonsObservable(): MutableLiveData<List<PokemonViewModel>> = pokemonsObserver
    override fun getPokemonObservable(): MutableLiveData<PokemonViewModel> = pokemonObserver
    override fun getPaginatedPokemonsObservable(): MutableLiveData<List<PokemonViewModel>> = paginatedPokemonsObserver



}