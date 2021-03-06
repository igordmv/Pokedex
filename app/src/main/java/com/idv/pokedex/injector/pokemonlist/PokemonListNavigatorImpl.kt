package com.idv.pokedex.injector.pokemonlist

import android.content.Intent
import com.idv.core.contrats.PokemonListNavigator
import com.idv.pokedex.view.MainActivity
import com.idv.pokemondetails.view.PokemonDetailsActivity
import java.lang.ref.WeakReference

internal class PokemonListNavigatorImpl(private val activityRef: WeakReference<MainActivity>) : PokemonListNavigator {
    override fun navigatePokemonDetails(pokemonName: String) {
        activityRef.get()?.let {
            val intent = Intent(it, PokemonDetailsActivity::class.java)
            intent.putExtra(PokemonDetailsActivity.POKEMON_IDENTIFIER_ID, pokemonName)
            it.startActivity(intent)
        }
    }
}