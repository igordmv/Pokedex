package com.idv.pokemon.service

import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import java.io.IOException

internal interface PokemonService {
    @Throws(IOException::class)
    suspend fun getPokemon(identifier : String) : PokemonResponseModel

    @Throws(IOException::class)
    suspend fun getPokemons(offset : Int? = 0) : PokemonsResponseModel

}
