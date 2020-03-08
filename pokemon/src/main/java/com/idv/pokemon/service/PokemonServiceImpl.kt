package com.idv.pokemon.service

import com.idv.core.service.ServiceFactory
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import java.io.IOException

internal class PokemonServiceImpl(factory: ServiceFactory) : PokemonService {
    private val service: PokemonRetrofitService =
        factory.make(BASE_URL, PokemonRetrofitService::class.java)

    override suspend fun getPokemon(identifier: String): PokemonResponseModel {
        try {
            val response = service.getPokemon(POKEMON_URL + identifier).execute()
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
    }

}
