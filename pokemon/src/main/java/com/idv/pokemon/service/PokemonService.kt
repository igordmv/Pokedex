package com.idv.pokemon.service

import com.idv.pokemon.service.retrofitmodel.PokemonDetailsRespondeModel
import com.idv.pokemon.service.retrofitmodel.PokemonEffectEntriesResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import java.io.IOException

internal interface PokemonService {
    @Throws(IOException::class)
    suspend fun getPokemon(identifier : String) : PokemonResponseModel

    @Throws(IOException::class)
    suspend fun getPokemons(offset : Int) : PokemonsResponseModel

    @Throws(IOException::class)
    suspend fun getPokemonDetails(identifier: String) : PokemonDetailsRespondeModel

    @Throws(IOException::class)
    fun getAbilityDetails(abilityName: String): PokemonEffectEntriesResponseModel

}
