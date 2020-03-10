package com.idv.pokemon_list.view.mapper

import com.idv.pokemon_list.view.presenter.PokemonViewModel
import com.idv.pokemon_entity.Pokemon

internal class MainMapperImpl : MainMapper {
    override fun mapPokemon(pokemon: Pokemon): PokemonViewModel {
        return PokemonViewModel(pokemon.name, pokemon.id, pokemon.image)
    }

    override fun mapPokemons(pokemons: List<Pokemon>): List<PokemonViewModel> =
        pokemons.map { pokemon -> mapPokemonToPokemonViewModel(pokemon) }

    private fun mapPokemonToPokemonViewModel(pokemon: Pokemon): PokemonViewModel {
        return PokemonViewModel(pokemon.name, pokemon.id, pokemon.image)
    }

}