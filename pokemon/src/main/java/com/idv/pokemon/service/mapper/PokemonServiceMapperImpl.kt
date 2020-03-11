package com.idv.pokemon.service.mapper

import android.util.Log
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import com.idv.pokemon.service.retrofitmodel.ResultResponseModel
import com.idv.pokemon_entity.Pokemon

internal class PokemonServiceMapperImpl : PokemonServiceMapper {
    override fun mapPokemon(body: PokemonResponseModel): Pokemon {
        return Pokemon(
            body.name ?: "not found",
            body.sprites?.front ?: ""
        )
    }

    override fun mapPokemons(pokemons: PokemonsResponseModel): List<Pokemon> {
       val pokemons =  pokemons.result?.map { result -> mapTest(result) }
        return pokemons?: emptyList()
    }

    private fun mapTest(result: ResultResponseModel) : Pokemon {
        val id = result.url?.replace("v2".toRegex(), "")?.replace("[^0-9]".toRegex(), "")
        Log.e("test", URL + id)
        return Pokemon(result.name?: "not found", "$URL$id.png")
    }

    companion object {
        private const val URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    }
}