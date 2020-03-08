package com.idv.pokemon.usecases.get

import com.idv.core.service.ServiceFactory
import com.idv.pokemon.service.PokemonServiceImpl
import com.idv.pokemon.service.mapper.PokemonServiceMapper
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonEvolutionChain
import java.io.IOException

interface PokemonGetter {

    @Throws(IOException::class)
    suspend fun getPokemon(identifier : String) : Pokemon

    @Throws(IOException::class)
    suspend fun getPokemonEvolutionChain(identifier: String) : PokemonEvolutionChain

    class Builder(){

        private lateinit var factory: ServiceFactory

        fun setRetrofitFactory(factory: ServiceFactory) = apply {
            this.factory = factory
        }

        fun build() : PokemonGetter {
            val mapper = PokemonServiceMapper.make()
            val service = PokemonServiceImpl(factory)
            return PokemonGetterImpl(service, mapper)
        }
    }
}