package com.idv.pokemon.service

import com.idv.core.service.ServiceFactory
import com.idv.pokemon.service.retrofitmodel.PokemonDetailsRespondeModel
import com.idv.pokemon.service.retrofitmodel.PokemonEffectEntriesResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import java.io.IOException

internal class PokemonServiceImpl(factory: ServiceFactory) : PokemonService {
    private val service: PokemonRetrofitService =
        factory.make(BASE_URL, PokemonRetrofitService::class.java)

    override suspend fun getPokemon(identifier: String): PokemonResponseModel {
        try {
            val response = service.getPokemon(POKEMON_URL + identifier).execute()
            if(response.errorBody()?.string() == "Not Found") {
                throw IOException("Not Found")
            }

            return response.body()!!
        } catch (e: Exception) {
            throw IOException(e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getPokemons(offset : Int): PokemonsResponseModel {
        try {
            val response = service.getPokemons(offset?: 0).execute()
            return response.body()!!
        } catch (e: Exception) {
            throw IOException(e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getPokemonDetails(identifier: String) : PokemonDetailsRespondeModel {
        try {
            val response = service.getPokemonDetails(POKEMON_URL + identifier).execute()
            return response.body()!!
        } catch (e: Exception) {
            throw IOException(e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    override fun getAbilityDetails(abilityName: String): PokemonEffectEntriesResponseModel {
        try {
            val response = service.getAbilityDetails(ABILITY_URL + abilityName).execute()
            return response.body()!!
        } catch (e: Exception) {
            throw IOException(e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val POKEMON_URL = BASE_URL + "pokemon/"
        private const val ABILITY_URL = BASE_URL + "ability/"
        private const val POKEMON_SPECIES_URL = BASE_URL + "pokemon-species/"
    }

}
