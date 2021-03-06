package com.idv.pokemon_list.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_list.view.mapper.PokemonListMapper
import com.idv.pokemon_entity.Pokemon

internal interface PokemonListPresenter {

    fun presentPokemon(pokemon: Pokemon?)

    fun presentError()

    fun presentLoadingState(showLoading : Boolean)

    fun presentPaginateLoadingState(showLoading : Boolean)

    fun getErrorObservable(): MutableLiveData<Boolean>

    fun getLoadingObservable(): MutableLiveData<Boolean>

    fun getPaginateLoadingObservable(): MutableLiveData<Boolean>

    fun getPokemonsObservable(): MutableLiveData<List<PokemonViewModel>>

    fun getPokemonObservable(): MutableLiveData<PokemonViewModel>

    fun getPaginatedPokemonsObservable(): MutableLiveData<List<PokemonViewModel>>

    fun presentPokemons(pokemons: List<Pokemon>)

    fun presentPaginatedPokemons(pokemons: List<Pokemon>)

    companion object Factory {
        fun make() : PokemonListPresenter {
            val mapper = PokemonListMapper.make()
            return PokemonListPresenterImpl(mapper)
        }
    }
}