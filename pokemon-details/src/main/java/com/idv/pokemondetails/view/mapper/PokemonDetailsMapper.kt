package com.idv.pokemondetails.view.mapper

import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemondetails.view.PokemonAbilityDetailsViewModel
import com.idv.pokemondetails.view.PokemonDetailsViewModel
import com.idv.pokemondetails.view.TypedPokemonsViewModel

internal interface PokemonDetailsMapper {

    fun mapPokemonDetails(pokemonDetails : PokemonDetails) : PokemonDetailsViewModel

    fun mapAbilities(abilityDetails: PokemonAbility): PokemonAbilityDetailsViewModel

    fun mapTypedPokemons(pokemons: List<Pokemon>, type : String): List<TypedPokemonsViewModel>

    companion object Factory {

        fun make() : PokemonDetailsMapper {
            return PokemonDetailsMapperImpl()
        }
    }
}