package com.idv.pokemon_list.view.mapper

import com.idv.pokemon_list.view.presenter.PokemonViewModel
import com.idv.pokemon_entity.Pokemon

internal interface PokemonListMapper {

    fun mapPokemon(pokemon : Pokemon) : PokemonViewModel

    fun mapPokemons(pokemons: List<Pokemon>): List<PokemonViewModel>

    companion object Factory {
        fun make() : PokemonListMapper {
            return PokemonListMapperImpl()
        }
    }
}