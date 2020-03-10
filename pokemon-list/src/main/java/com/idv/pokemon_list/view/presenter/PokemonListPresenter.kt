package com.idv.pokemon_list.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_list.view.mapper.MainMapper
import com.idv.pokemon_entity.Pokemon

internal interface PokemonListPresenter {

    fun presentPokemon(pokemon: Pokemon)

    fun presentError()

    fun presentLoadingState(showLoading : Boolean)

    fun getErrorObservable(): MutableLiveData<Boolean>

    fun getLoadingObservable(): MutableLiveData<Boolean>

    fun getPokemonObservable(): MutableLiveData<List<PokemonViewModel>>

    fun presentPokemons(pokemons: List<Pokemon>)

    companion object Factory {
        fun make() : PokemonListPresenter {
            val mapper = MainMapper.make()
            return PokemonListPresenterImpl(mapper)
        }
    }
}