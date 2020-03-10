package com.idv.pokedex.view.mapper

import com.idv.pokedex.view.presenter.PokemonsViewModel
import com.idv.pokemon_entity.Pokemon

internal interface MainMapper {

    fun mapPokemon(pokemon : Pokemon) : PokemonsViewModel

    fun mapPokemons(pokemons: List<Pokemon>): PokemonsViewModel

    companion object Factory {
        fun make() : MainMapper{
            return MainMapperImpl()
        }
    }
}