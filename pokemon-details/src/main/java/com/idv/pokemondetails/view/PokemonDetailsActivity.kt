package com.idv.pokemondetails.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.globo.globosatplay.core.ImageConverter
import com.idv.core.extensions.runOnBackground
import com.idv.core.extensions.runOnUI
import com.idv.pokemondetails.PokemonDetailsController
import com.idv.pokemondetails.R
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetailsActivity : AppCompatActivity() {

    private var controller: PokemonDetailsController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        setSupportActionBar(searchToolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
        val identifier = intent.getStringExtra(POKEMON_IDENTIFIER_ID)

        controller = PokemonDetailsController
            .Builder()
            .setActivity(this)
            .setErrorObserver(errorObserver)
            .setLoadingObserver(loadingObserver)
            .setPokemonDetailsObserver(pokemonDetailsObserver)
            .build()

        identifier?.let {
            runOnBackground {
                controller?.getPokemon(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val pokemonDetailsObserver = Observer<PokemonDetailsViewModel> { pokemonDetails ->
        pokemonDetails?.let { pokemonDetails ->
            runOnUI {
                searchToolbar.title = pokemonDetails.name?.capitalize()
                pokemonName.text = pokemonDetails.name?.capitalize()

                pokemonDetails.defaultImage?.let { defaultImage ->
                    ImageConverter.load(
                        this,
                        defaultImage,
                        pokemonImage,
                        POKEMON_WIDTH,
                        POKEMON_HEIGHT
                    )
                }
                pokemonDetailsCardView.visibility = View.VISIBLE
            }
        }
    }

    private val loadingObserver = Observer<Boolean> { showLoading ->
        if (showLoading)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    private val errorObserver = Observer<Boolean> { hasError ->
        if (hasError) {
            Toast.makeText(this, TOAST_FAIL_REQUEST, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TOAST_FAIL_REQUEST = "Não foi possível fazer a requisição."
        const val POKEMON_IDENTIFIER_ID = "POKEMON_IDENTIFIER_ID"
        const val POKEMON_WIDTH = 300
        const val POKEMON_HEIGHT = 300
    }
}
