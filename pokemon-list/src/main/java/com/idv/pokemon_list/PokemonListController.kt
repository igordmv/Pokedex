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

    private var currentOffset : Int = 0
    var isScrolling : Boolean = false

    fun getPokemon(identifier : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemon = pokemonGetter.getPokemon(identifier.toLowerCase())
            presenter.presentPokemon(pokemon)
        } catch (e : IOException) {
            if(e.message == "Not Found")
                presenter.presentPokemon(null)
            else
                presenter.presentError()
        }
    }

    fun getFirstPokemons() = viewModelScope.launch(Dispatchers.IO) {
        try {
            presenter.presentLoadingState(true)
            val pokemons = pokemonGetter.getPokemons()
            currentOffset += PER_PAGE
            presenter.presentPokemons(pokemons)
        } catch (e : IOException) {
            presenter.presentError()
        }
    }

    fun getNextPage() = viewModelScope.launch(Dispatchers.IO) {
        try {
            isScrolling = true
            presenter.presentPaginateLoadingState(true)
            val pokemons = pokemonGetter.getPokemons(currentOffset)
            currentOffset += PER_PAGE
            presenter.presentPaginatedPokemons(pokemons)
        } catch (e : IOException) {
            presenter.presentError()
        } finally {
            isScrolling = false
        }
    }

    companion object {
        private const val PER_PAGE = 20
    }

    class Builder() {
        private lateinit var paginatedPokemonsObserver: Observer<List<PokemonViewModel>>
        private lateinit var fragmentRef: WeakReference<PokemonListFragment>
        private lateinit var loadingObserver: Observer<Boolean>
        private lateinit var paginateLoadingObserver: Observer<Boolean>
        private lateinit var errorObserver: Observer<Boolean>
        private lateinit var pokemonsObserver: Observer<List<PokemonViewModel>>
        private lateinit var pokemonObserver: Observer<PokemonViewModel>

        fun setActivity(fragment: PokemonListFragment) = apply {
            this.fragmentRef = WeakReference(fragment)
        }

        fun setErrorObserver(errorObserver: Observer<Boolean>) = apply {
            this.errorObserver = errorObserver
        }

        fun setPaginateLoadingObserver(loadingObserver: Observer<Boolean>) = apply {
            this.paginateLoadingObserver = loadingObserver
        }

        fun setLoadingObserver(loadingObserver: Observer<Boolean>) = apply {
            this.loadingObserver = loadingObserver
        }

        fun setPokemonsObserver(pokemonsObserver : Observer<List<PokemonViewModel>>) = apply {
            this.pokemonsObserver = pokemonsObserver
        }

        fun setPokemonObserver(pokemonObserver : Observer<PokemonViewModel>) = apply {
            this.pokemonObserver = pokemonObserver
        }

        fun setPaginatedPokemonsObserver(pokemonsObserver : Observer<List<PokemonViewModel>>) = apply {
            this.paginatedPokemonsObserver = pokemonsObserver
        }

        fun build() : PokemonListController? {
            var fragment = fragmentRef.get()
            fragment?.let { _ ->
                val presenter = PokemonListPresenter.make()
                presenter.getErrorObservable().observe(fragment, errorObserver)
                presenter.getLoadingObservable().observe(fragment, loadingObserver)
                presenter.getPokemonsObservable().observe(fragment, pokemonsObserver)
                presenter.getPokemonObservable().observe(fragment, pokemonObserver)
                presenter.getPaginatedPokemonsObservable().observe(fragment,paginatedPokemonsObserver)
                presenter.getPaginateLoadingObservable().observe(fragment,paginateLoadingObserver)

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