package com.idv.pokedex.injector.pokemonlist

import com.idv.core.contrats.PokemonListNavigator
import com.idv.pokedex.view.MainActivity
import com.idv.pokemon_list.view.PokemonListFragment
import java.lang.ref.WeakReference

internal class PokemonListFragmentApp : PokemonListFragment() {
    override val navigator: PokemonListNavigator
        get() = PokemonListNavigatorImpl(WeakReference(requireActivity() as MainActivity))
}