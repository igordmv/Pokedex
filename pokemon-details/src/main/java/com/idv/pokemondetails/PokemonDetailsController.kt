package com.idv.pokemondetails

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idv.core.service.RetrofitServiceFactory
import com.idv.pokemon.usecases.get.PokemonGetter
import com.idv.pokemondetails.view.PokemonAbilityDetailsViewModel
import com.idv.pokemondetails.view.PokemonDetailsActivity
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.presenter.PokemonDetailsPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.ref.WeakReference

internal class PokemonDetailsController(
    private val pokemonGetter: PokemonGetter,
    private val presenter: PokemonDetailsPresenter
) : ViewModel() {

    fun getPokemon(identifier: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemonDetails = pokemonGetter.getPokemonDetails(identifier.toLowerCase())
            presenter.presentPokemonDetails(pokemonDetails)
        } catch (e: IOException) {
            presenter.presentError()
        }
    }

    fun getAbilityDetails(ability : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val abilityDetails = pokemonGetter.getAbilityDetails(ability.toLowerCase())
            presenter.presentAbilityDetails(abilityDetails)
        } catch (e: IOException) {
            presenter.presentError()
        }
    }

    fun getPokemonsByType(type : String ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemons = pokemonGetter.getPokemonsByType(type.toLowerCase())
            presenter.presentTypePokemons(pokemons)
        } catch (e: IOException) {
            presenter.presentError()
        }
    }


    class Builder() {

        private lateinit var activityRef: WeakReference<PokemonDetailsActivity>
        private lateinit var loadingObserver: Observer<Boolean>
        private lateinit var errorObserver: Observer<Boolean>
        private lateinit var pokemonDetailsObserver: Observer<PokemonDetailsViewModel>
        private lateinit var abilityDetailsObserver: Observer<PokemonAbilityDetailsViewModel>

        fun setActivity(activity: PokemonDetailsActivity) = apply {
            this.activityRef = WeakReference(activity)
        }

        fun setErrorObserver(errorObserver: Observer<Boolean>) = apply {
            this.errorObserver = errorObserver
        }

        fun setLoadingObserver(loadingObserver: Observer<Boolean>) = apply {
            this.loadingObserver = loadingObserver
        }

        fun setPokemonDetailsObserver(pokemonDetailsObserver: Observer<PokemonDetailsViewModel>) = apply {
            this.pokemonDetailsObserver = pokemonDetailsObserver
        }

        fun setAbilityDetailsObserver(abilityDetailsObserver: Observer<PokemonAbilityDetailsViewModel>) = apply {
            this.abilityDetailsObserver = abilityDetailsObserver
        }

        fun build(): PokemonDetailsController? {
            var activity = activityRef.get()
            activity?.let { _ ->
                val presenter = PokemonDetailsPresenter.make()
                presenter.getErrorObservable().observe(activity, errorObserver)
                presenter.getLoadingObservable().observe(activity, loadingObserver)
                presenter.getPokemonDetailsObservable().observe(activity, pokemonDetailsObserver)
                presenter.getAbilityDetailsObservable().observe(activity, abilityDetailsObserver)

                val pokemonGetter =
                    PokemonGetter.Builder().setRetrofitFactory(RetrofitServiceFactory).build()

                return PokemonDetailsController(
                    pokemonGetter,
                    presenter
                )
            }
            return null
        }
    }

}