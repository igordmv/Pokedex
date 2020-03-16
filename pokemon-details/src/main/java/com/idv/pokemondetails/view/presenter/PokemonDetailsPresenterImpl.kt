package com.idv.pokemondetails.view.presenter

import androidx.lifecycle.MutableLiveData
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonAbilityDetailsViewModel
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.TypedPokemonsViewModel
import com.idv.pokemondetails.view.mapper.PokemonDetailsMapper

internal class PokemonDetailsPresenterImpl(private val mapper: PokemonDetailsMapper) : PokemonDetailsPresenter {

    private val loadingObserver = MutableLiveData<Boolean>()
    private val errorObserver = MutableLiveData<Boolean>()
    private val pokemonDetailsObserver = MutableLiveData<PokemonDetailsViewModel>()
    private val abilityDetailsObserver = MutableLiveData<PokemonAbilityDetailsViewModel>()
    private val typedPokemonsObserver = MutableLiveData<List<TypedPokemonsViewModel>>()

    override fun presentPokemonDetails(pokemonDetails: PokemonDetails) {
        val mappedDetails = mapper.mapPokemonDetails(pokemonDetails)
        pokemonDetailsObserver.postValue(mappedDetails)
        loadingObserver.postValue(false)
        errorObserver.postValue(false)
    }

    override fun presentAbilityDetails(abilityDetails: PokemonAbility) {
        val mappedAbilities = mapper.mapAbilities(abilityDetails)
        abilityDetailsObserver.postValue(mappedAbilities)
        loadingObserver.postValue(false)
    }

    override fun presentTypePokemons(pokemons: List<Pokemon>, type: String) {
        val mappedTypedPokemons = mapper.mapTypedPokemons(pokemons, type)
        typedPokemonsObserver.postValue(mappedTypedPokemons)
        loadingObserver.postValue(false)

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
    override fun getAbilityDetailsObservable(): MutableLiveData<PokemonAbilityDetailsViewModel> = abilityDetailsObserver
    override fun getTypedPokemonsObservable(): MutableLiveData<List<TypedPokemonsViewModel>> = typedPokemonsObserver


}