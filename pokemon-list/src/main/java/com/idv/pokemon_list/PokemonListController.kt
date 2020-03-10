package com.idv.pokemon_list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idv.core.service.RetrofitServiceFactory
import com.idv.pokemon.usecases.get.PokemonGetter
import com.idv.pokemon_list.view.PokemonListFragment
import com.idv.pokemon_list.view.presenter.PokemonListPresenter
import com.idv.pokemon_list.view.presenter.PokemonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.ref.WeakReference

internal class PokemonListController(
    private val pokemonGetter: PokemonGetter,
    private val presenter: PokemonListPresenter
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
        private lateinit var fragmentRef: WeakReference<PokemonListFragment>
        private lateinit var loadingObserver: Observer<Boolean>
        private lateinit var errorObserver: Observer<Boolean>
        private lateinit var pokemonsObserver: Observer<List<PokemonViewModel>>

        fun setActivity(fragment: PokemonListFragment) = apply {
            this.fragmentRef = WeakReference(fragment)
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

        fun build() : PokemonListController? {
            var fragment = fragmentRef.get()
            fragment?.let { _ ->
                val presenter = PokemonListPresenter.make()
                presenter.getErrorObservable().observe(fragment, errorObserver)
                presenter.getLoadingObservable().observe(fragment, loadingObserver)
                presenter.getPokemonObservable().observe(fragment, pokemonsObserver)


                val pokemonGetter =
                    PokemonGetter.Builder().setRetrofitFactory(RetrofitServiceFactory).build()

                return PokemonListController(
                    pokemonGetter,
                    presenter
                )
            }
            return null
        }

    }
}