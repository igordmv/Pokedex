package com.idv.pokemondetails.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globo.globosatplay.core.ImageConverter
import com.idv.core.extensions.runOnUI
import com.idv.pokemondetails.PokemonDetailsController
import com.idv.pokemondetails.R
import com.idv.pokemondetails.view.adapter.PokemonChainAdapter
import com.idv.pokemondetails.view.adapter.PokemonDetailsAdapter
import com.idv.pokemondetails.view.adapter.PokemonTypeAdapter
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.android.synthetic.main.dialog_ability.view.*
import kotlinx.android.synthetic.main.dialog_typed_pokemons.view.*

class PokemonDetailsActivity : AppCompatActivity() {

    private var controller: PokemonDetailsController? = null
    private lateinit var pokemonImagesAdapter: PokemonDetailsAdapter
    private lateinit var typedPokemonAdapter: PokemonTypeAdapter
    private lateinit var chainPokemonAdapter: PokemonChainAdapter
    private var typedDialog : AlertDialog? = null

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
            .setAbilityDetailsObserver(pokemonAbilityObserver)
            .setTypedPokemonsObserver(typedPokemonsObserver)
            .build()

        identifier?.let {
            runOnUI {
                searchToolbar.title = it.capitalize()
                controller?.getPokemon(it)
            }
        }
    }

    fun refreshContent(pokemon : String) = runOnUI {
        typedDialog?.let { it.dismiss()}
        pokemonTypeButtonOne.visibility = View.GONE
        pokemonTypeButtonTwo.visibility = View.GONE
        pokemonTypeButtonTree.visibility = View.GONE

        pokemonAbilityButtonOne.visibility = View.GONE
        pokemonAbilityButtonTwo.visibility = View.GONE
        pokemonAbilityButtonTree.visibility = View.GONE

        searchToolbar.title = pokemon.capitalize()
        controller?.getPokemon(pokemon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val typedPokemonsObserver = Observer<List<TypedPokemonsViewModel>> { type ->
        runOnUI {
            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_typed_pokemons, null)
            dialogBuilder.setView(dialogView)
            typedDialog = dialogBuilder.create()
            typedDialog?.show()
            dialogView.pokemonType.text = type.first().type.capitalize()
            typedPokemonAdapter =
                PokemonTypeAdapter(
                    this,
                    type as MutableList<TypedPokemonsViewModel>
                )
            val mLayoutManager = LinearLayoutManager(this)
            mLayoutManager.orientation = RecyclerView.HORIZONTAL
            dialogView.pokemonTypeImageRecyclerView?.apply {
                layoutManager = mLayoutManager
                adapter = typedPokemonAdapter
                visibility = View.VISIBLE
            }

        }
    }

    private val pokemonAbilityObserver = Observer<PokemonAbilityDetailsViewModel> { ability ->
        runOnUI {
            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_ability, null)
            dialogBuilder.setView(dialogView)
            val dialog = dialogBuilder.create()
            dialog.show()
            dialogView.abilityTitle.text = ability.title.capitalize()
            dialogView.abilityEffect.text = ability.effect
            dialogView.abilityShortEffect.text = ability.shortEffect
        }
    }

    private val pokemonDetailsObserver = Observer<PokemonDetailsViewModel> { pokemonDetails ->
        pokemonDetails?.let { pokemonDetails ->
            runOnUI {
                searchToolbar.title = pokemonDetails.name?.capitalize()
                pokemonDetails.defaultImage?.let { defaultImage ->
                    ImageConverter.load(
                        this,
                        defaultImage,
                        pokemonImage,
                        POKEMON_WIDTH,
                        POKEMON_HEIGHT
                    )
                }
                pokemonDetails.images?.let {
                    pokemonImagesAdapter =
                        PokemonDetailsAdapter(
                            this,
                            it as MutableList<String>
                        )
                    val mLayoutManager = LinearLayoutManager(this)
                    mLayoutManager.orientation = RecyclerView.HORIZONTAL
                    pokemonImageRecyclerView?.apply {
                        layoutManager = mLayoutManager
                        adapter = pokemonImagesAdapter
                        visibility = View.VISIBLE
                    }
                }

                pokemonDetails.hitPoints?.let { hp ->
                    pokemonHp.text = getString(R.string.hp, hp)
                }
                pokemonDetails.speed?.let { speed ->
                    pokemonSpeed.text = getString(R.string.speed, speed)
                }
                pokemonDetails.attack?.let { attack ->
                    pokemonAttack.text = getString(R.string.attack, attack)
                }
                pokemonDetails.specialAttack?.let { specialAttack ->
                    pokemonSpecialAttack.text = getString(R.string.specialAttack, specialAttack)
                }
                pokemonDetails.defence?.let { defence ->
                    pokemonDefence.text = getString(R.string.defence, defence)
                }
                pokemonDetails.specialDefence?.let { specialDefence ->
                    pokemonSpecialDeffence.text = getString(R.string.specialDefence, specialDefence)
                }
                pokemonDetails.height?.let {
                    pokemonHeight.text = getString(R.string.height, it)
                }
                pokemonDetails.weight?.let {
                    pokemonWeight.text = getString(R.string.weight, it)
                }
                pokemonDetails.types?.let { types ->
                    if (types.isNotEmpty()) {
                        pokemonTypeButtonOne.text = types[0]
                        pokemonTypeButtonOne.visibility = View.VISIBLE
                        pokemonTypeButtonOne.setOnClickListener {
                            controller?.getPokemonsByType(types[0].toLowerCase())
                        }
                    }
                    if (types.size >= 2) {
                        pokemonTypeButtonTwo.text = types[1]
                        pokemonTypeButtonTwo.visibility = View.VISIBLE
                        pokemonTypeButtonTwo.setOnClickListener {
                            controller?.getPokemonsByType(types[1].toLowerCase())
                        }
                    }
                    if (types.size >= 3) {
                        pokemonTypeButtonTree.text = types[2]
                        pokemonTypeButtonTree.visibility = View.VISIBLE
                        pokemonTypeButtonTree.setOnClickListener {
                            controller?.getPokemonsByType(types[2].toLowerCase())
                        }
                    }
                }
                pokemonDetails.abilities?.let { abilities ->
                    if (abilities.isNotEmpty()) {
                        pokemonAbilityButtonOne.text = abilities[0]
                        pokemonAbilityButtonOne.visibility = View.VISIBLE
                        pokemonAbilityButtonOne.setOnClickListener {
                            controller?.getAbilityDetails(abilities[0].toLowerCase())
                        }
                    }
                    if (abilities.size >= 2) {
                        pokemonAbilityButtonTwo.text = abilities[1]
                        pokemonAbilityButtonTwo.visibility = View.VISIBLE
                        pokemonAbilityButtonTwo.setOnClickListener {
                            controller?.getAbilityDetails(abilities[1].toLowerCase())
                        }
                    }
                    if (abilities.size >= 3) {
                        pokemonAbilityButtonTree.text = abilities[2]
                        pokemonAbilityButtonTree.visibility = View.VISIBLE
                        pokemonAbilityButtonTree.setOnClickListener {
                            controller?.getAbilityDetails(abilities[2].toLowerCase())
                        }
                    }
                }
                pokemonDetails.evolutionChain?.let { evolutionChain ->
                    evolutionChainText.visibility = View.VISIBLE
                    chainPokemonAdapter =
                        PokemonChainAdapter(
                            this,
                            evolutionChain as MutableList<PokemonEvolutionChainViewModel>
                        )
                    val mLayoutManager = LinearLayoutManager(this)
                    mLayoutManager.orientation = RecyclerView.HORIZONTAL
                    pokemonChainRecyclerView?.apply {
                        layoutManager = mLayoutManager
                        adapter = chainPokemonAdapter
                        visibility = View.VISIBLE
                    }
                }

                pokemonName.text = pokemonDetails.name?.capitalize()
            }
        }
    }

    private fun createAbilityDialog(ability: String) = runOnUI {

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
