package com.idv.pokedex.view.mapper

import com.idv.pokedex.view.presenter.PokemonViewModel
import com.idv.pokedex.view.presenter.PokemonsViewModel
import com.idv.pokemon_entity.Pokemon

internal class MainMapperImpl : MainMapper {
    override fun mapPokemon(pokemon: Pokemon): PokemonsViewModel {
        return PokemonsViewModel(listOf(PokemonViewModel(pokemon.name)))
    }

    override fun mapPokemons(pokemons: List<Pokemon>): PokemonsViewModel =
        PokemonsViewModel( pokemons.map { pokemon -> mapPokemonToPokemonViewModel(pokemon) })

    private fun mapPokemonToPokemonViewModel(pokemon: Pokemon): PokemonViewModel {
        return PokemonViewModel(pokemon.name)
    }

}