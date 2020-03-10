package com.idv.pokedex

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idv.core.service.RetrofitServiceFactory
import com.idv.pokedex.view.MainActivity
import com.idv.pokedex.view.presenter.MainPresenter
import com.idv.pokedex.view.presenter.PokemonViewModel
import com.idv.pokemon.usecases.get.PokemonGetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.ref.WeakReference

internal class MainController(
    private val pokemonGetter: PokemonGetter,
    private val presenter: MainPresenter
) : ViewModel()  {

    fun getPokemon(identifier : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemon = pokemonGetter.getPokemon(identifier)
            presenter.presentPokemon(pokemon)
        } catch (e : IOException) {
            presenter.presentError()
        }
    }

    fun getFirstPokemons() = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemons = pokemonGetter.getPokemons()
            presenter.presentPokemons(pokemons)
        } catch (e : IOException) {
            presenter.presentError()
        }
    }

    class Builder() {
        private lateinit var activityRef: WeakReference<MainActivity>
        private lateinit var loadingObserver: Observer<Boolean>
        private lateinit var errorObserver: Observer<Boolean>
        private lateinit var pokemonsObserver: Observer<List<PokemonViewModel>>

        fun setActivity(activity: MainActivity) = apply {
            this.activityRef = WeakReference(activity)
        }

        fun setErrorObserver(errorObserver: Observer<Boolean>) = apply {
            this.errorObserver = errorObserver
        }

        fun setLoadingObserver(loadingObserver: Observer<Boolean>) = apply {
            this.loadingObserver = loadingObserver
        }

        fun setPokemonsObserver(pokemonsObserver : Observer<List<PokemonViewModel>>) = apply {
            this.pokemonsObserver = pokemonsObserver
        }

        fun build() : MainController? {
            var activity = activityRef.get()
            activity?.let { _ ->
                val presenter = MainPresenter.make()
                presenter.getErrorObservable().observe(activity, errorObserver)
                presenter.getLoadingObservable().observe(activity, loadingObserver)
                presenter.getPokemonObservable().observe(activity, pokemonsObserver)


                val pokemonGetter =
                    PokemonGetter.Builder().setRetrofitFactory(RetrofitServiceFactory).build()

                return MainController(pokemonGetter, presenter)
            }
            return null
        }

    }
}