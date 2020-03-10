package com.idv.pokedex.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokedex.view.mapper.MainMapper
import com.idv.pokemon_entity.Pokemon

internal interface MainPresenter {

    fun presentPokemon(pokemon: Pokemon)

    fun presentError()

    fun presentLoadingState(showLoading : Boolean)

    fun getErrorObservable(): MutableLiveData<Boolean>

    fun getLoadingObservable(): MutableLiveData<Boolean>

    fun getPokemonObservable(): MutableLiveData<PokemonsViewModel>

    fun presentPokemons(pokemons: List<Pokemon>)

    companion object Factory {
        fun make() : MainPresenter{
            val mapper = MainMapper.make()
            return MainPresenterImpl(mapper)
        }
    }
}