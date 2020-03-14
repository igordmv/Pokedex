package com.idv.pokemon.service.mapper

import com.idv.pokemon.service.retrofitmodel.*
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails

internal interface PokemonServiceMapper {
    fun mapPokemon(body : PokemonResponseModel) : Pokemon

    fun mapPokemons(pokemons: PokemonsResponseModel): List<Pokemon>

    fun mapPokemonDetails(pokemonDetails: PokemonDetailsResponseModel) : PokemonDetails

    fun mapAbilities(abilityName: String, abilityDetails: PokemonEffectEntriesResponseModel): PokemonAbility

    fun mapPokemonByType(pokemons: PokemonsTypeResponseModel): List<Pokemon>

    companion object Factory {
        fun make() : PokemonServiceMapper {
            return PokemonServiceMapperImpl()
        }
    }
}