package com.idv.pokedex.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.globo.globosatplay.core.fragment.StatelessFragment
import com.idv.pokedex.MainController
import com.idv.pokedex.R
import com.idv.pokedex.injector.pokemonlist.PokemonListFragmentApp
import com.idv.pokemon_list.view.PokemonListFragment


class MainActivity : AppCompatActivity() {

    private var currentFragment: StatelessFragment? = null
    private var lastFragment: StatelessFragment? = null
    private var controller: MainController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = MainController.Builder()
            .setContext(this)
            .setPokemonListObservable(showPokemonListObservable)
            .build()

        if (savedInstanceState == null) {
            controller?.onViewCreated()
        } else {
            val fragment: PokemonListFragmentApp? =
                supportFragmentManager.findFragmentByTag(POKEMON_LIST_FRAGMENT_TAG) as PokemonListFragmentApp?

            currentFragment = fragment
            supportFragmentManager.beginTransaction()
                .replace(FRAGMENTS_CONTAINER, fragment as StatelessFragment, POKEMON_LIST_FRAGMENT_TAG)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit()
        }

    }

    private val showPokemonListObservable = Observer<Boolean> { showPokemonList ->
        if (showPokemonList) {
            if (currentFragment !is PokemonListFragmentApp) {
                lastFragment = currentFragment
                currentFragment?.saveState(supportFragmentManager)
                currentFragment = PokemonListFragmentApp()
                replaceFragmentToLastState(currentFragment, POKEMON_LIST_FRAGMENT_TAG)
            }
        }
    }

    private fun replaceFragmentToLastState(
        currentFragment: StatelessFragment?,
        fragmentTag: String
    ) {
        currentFragment?.let { fragment ->
            fragment.restoreState()
            supportFragmentManager.beginTransaction()
                .replace(FRAGMENTS_CONTAINER, fragment, fragmentTag)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            val message = intent.getStringExtra(SearchManager.QUERY)
            if (currentFragment is PokemonListFragmentApp) {
                (currentFragment as PokemonListFragmentApp).setSpelledQuery(message)
            }
        }
    }

    companion object {
        const val FRAGMENTS_CONTAINER = R.id.content
        const val POKEMON_LIST_FRAGMENT_TAG = "pokemonListFragment"
    }
}
