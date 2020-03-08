package com.idv.pokemon.service.mapper

import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon_entity.Pokemon

internal interface PokemonServiceMapper {
    fun mapPokemon(body : PokemonResponseModel) : Pokemon

    companion object Factory {
        fun make() : PokemonServiceMapper {
            return PokemonServiceMapperImpl()
        }
    }
}