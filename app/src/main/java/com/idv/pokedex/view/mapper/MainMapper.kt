package com.idv.pokedex.view.mapper

import com.idv.pokedex.view.presenter.PokemonViewModel
import com.idv.pokemon_entity.Pokemon

internal interface MainMapper {

    fun map(pokemon : Pokemon) : PokemonViewModel

    companion object Factory {
        fun make() : MainMapper{
            return MainMapperImpl()
        }
    }
}