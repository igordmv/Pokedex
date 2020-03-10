package com.idv.pokedex.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globo.globosatplay.core.ImageConverter
import com.idv.pokedex.R
import com.idv.pokedex.view.presenter.PokemonViewModel
import kotlinx.android.synthetic.main.view_holder_pokemon.view.*
import java.lang.ref.WeakReference

internal class PokemonAdapter(private val context : Context, private val pokemons : List<PokemonViewModel>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_holder_pokemon, parent, false)
        return PokemonViewHolder(view, WeakReference(context))
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentResult = pokemons.get(position)
        holder.let {
            it.bindView(currentResult)
        }
    }

    class PokemonViewHolder(itemView: View, private val activityRef: WeakReference<Context>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var view: View = itemView
        private var pokemon: PokemonViewModel? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindView(result: PokemonViewModel){
            val text = itemView.pokemonName
            val imageView = itemView.pokemonImage
            text.text = result.name
            activityRef.get()?.let { context ->
                ImageConverter.load(context, result.image, imageView, 250 ,250 )
            }

            itemView.setOnClickListener(this)
        }

    }
}