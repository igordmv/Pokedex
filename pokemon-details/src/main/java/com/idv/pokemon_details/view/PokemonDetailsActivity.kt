package com.idv.pokemon_details.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.globo.globosatplay.core.ImageConverter
import com.idv.core.extensions.runOnUI
import com.idv.pokemon_details.R
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        setSupportActionBar(searchToolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
        val identifier = intent.getStringExtra(POKEMON_IDENTIFIER_ID)

        runOnUI {
            identifier?.let {
                searchToolbar.title = it
                pokemonName.text = it
                ImageConverter.load(
                    this,
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                    pokemonImage,
                    300,
                    300
                )
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

    companion object {
        const val POKEMON_IDENTIFIER_ID = "POKEMON_IDENTIFIER_ID"
    }
}
