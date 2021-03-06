package com.idv.pokemondetails.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonAbilityDetailsViewModel
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.TypedPokemonsViewModel
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper

internal interface PokemonDetailsPresenter {

    fun presentError()

    fun presentLoadingState(showLoading : Boolean)

    fun presentPokemonDetails(pokemonDetails: PokemonDetails)

    fun presentAbilityDetails(abilityDetails: PokemonAbility)

    fun getErrorObservable(): MutableLiveData<Boolean>

    fun getLoadingObservable(): MutableLiveData<Boolean>

    fun getPokemonDetailsObservable(): MutableLiveData<PokemonDetailsViewModel>

    fun getAbilityDetailsObservable(): MutableLiveData<PokemonAbilityDetailsViewModel>

    fun presentTypePokemons(pokemons: List<Pokemon>, type: String)

    fun getTypedPokemonsObservable(): MutableLiveData<List<TypedPokemonsViewModel>>

    companion object Factory {
        fun make() : PokemonDetailsPresenter {
            val mapper = PokemonDetailsMapper.make()
            return PokemonDetailsPresenterImpl(mapper)
        }
    }

}