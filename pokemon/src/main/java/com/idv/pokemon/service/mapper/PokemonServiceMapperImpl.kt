package com.idv.pokemon.service.mapper

import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon_entity.Pokemon

internal class PokemonServiceMapperImpl : PokemonServiceMapper{
    override fun mapPokemon(body: PokemonResponseModel): Pokemon {
        return Pokemon(body.name?: "not found")
    }
}