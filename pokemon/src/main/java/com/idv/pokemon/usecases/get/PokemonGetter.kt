package com.idv.pokemon.usecases.get

import com.idv.core.service.ServiceFactory
import com.idv.pokemon.service.PokemonServiceImpl
import com.idv.pokemon.service.mapper.PokemonServiceMapper
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemon_entity.PokemonEvolutionChain
import java.io.IOException

interface PokemonGetter {

    @Throws(IOException::class)
    suspend fun getPokemon(identifier : String) : Pokemon

    @Throws(IOException::class)
    suspend fun getPokemonDetails(identifier : String) : PokemonDetails

    @Throws(IOException::class)
    suspend fun getPokemons(offset : Int? = 0) : List<Pokemon>

    @Throws(IOException::class)
    suspend fun getPokemonEvolutionChain(identifier: String) : PokemonEvolutionChain

    @Throws(IOException::class)
    suspend fun getAbilityDetails(abilityName : String) : PokemonAbility

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