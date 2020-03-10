package com.idv.pokemon_list.view.mapper

import com.idv.pokemon_list.view.presenter.PokemonViewModel
import com.idv.pokemon_entity.Pokemon

internal interface MainMapper {

    fun mapPokemon(pokemon : Pokemon) : PokemonViewModel

    fun mapPokemons(pokemons: List<Pokemon>): List<PokemonViewModel>

    companion object Factory {
        fun make() : MainMapper {
            return MainMapperImpl()
        }
    }
}