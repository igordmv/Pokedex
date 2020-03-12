package com.idv.pokemon.service.mapper

import com.idv.pokemon.service.retrofitmodel.PokemonDetailsRespondeModel
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonDetails

internal interface PokemonServiceMapper {
    fun mapPokemon(body : PokemonResponseModel) : Pokemon

    fun mapPokemons(pokemons: PokemonsResponseModel): List<Pokemon>

    fun mapPokemonDetails(pokemonDetails: PokemonDetailsRespondeModel) : PokemonDetails

    companion object Factory {
        fun make() : PokemonServiceMapper {
            return PokemonServiceMapperImpl()
        }
    }
}