package com.idv.pokemon_details.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.idv.core.extensions.runOnUI
import com.idv.pokemon_details.R
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        val identifier = intent.getStringExtra(POKEMON_IDENTIFIER_ID)
        runOnUI {
            identifier?.let {
                pokemonName.text = it
            }
        }
    }

    companion object {
        const val POKEMON_IDENTIFIER_ID = "POKEMON_IDENTIFIER_ID"
    }
}
