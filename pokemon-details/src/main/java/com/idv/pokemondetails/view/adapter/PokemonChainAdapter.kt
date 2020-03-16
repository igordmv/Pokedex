package com.idv.pokemondetails.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globo.globosatplay.core.ImageConverter
import com.idv.pokemondetails.R
import com.idv.pokemondetails.view.PokemonDetailsActivity
import com.idv.pokemondetails.view.PokemonEvolutionChainViewModel
import kotlinx.android.synthetic.main.view_holder_named_pokemon_horizontal.view.*
import java.lang.ref.WeakReference


internal class PokemonChainAdapter(private val activity : PokemonDetailsActivity, private val pokemons : MutableList<PokemonEvolutionChainViewModel>) : RecyclerView.Adapter<PokemonChainAdapter.PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.view_holder_named_pokemon_horizontal, parent, false)
        return PokemonViewHolder(
            view,
            WeakReference(activity)
        )
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentResult = pokemons.get(position)
        holder.let {
            it.bindView(currentResult)
        }
    }

    class PokemonViewHolder(itemView: View, private val activityRef: WeakReference<PokemonDetailsActivity>) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var view: View = itemView
        private var pokemonUrl: String? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

        }

        fun bindView(result: PokemonEvolutionChainViewModel){
            val imageView = itemView.typedPokemonImage
            val textView = itemView.typedPokemonName

            textView.text = result.name.capitalize()

            activityRef.get()?.let { activity ->
                ImageConverter.load(
                    activity,
                    result.image,
                    imageView,
                    POKEMON_WIDTH,
                    POKEMON_HEIGHT
                )
            }

            imageView.setOnClickListener {
                activityRef.get()?.let {
                    it.refreshContent(result.name.toLowerCase())
                }
            }
        }
        companion object {
            const val POKEMON_WIDTH = 400
            const val POKEMON_HEIGHT = 400
        }

    }
}