package com.idv.pokemon.usecases.get

import com.idv.pokemon.service.PokemonService
import com.idv.pokemon.service.mapper.PokemonServiceMapper
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonAbility
import com.idv.pokemon_entity.PokemonDetails
import com.idv.pokemon_entity.PokemonEvolutionChain
import java.io.IOException

internal class PokemonGetterImpl(
    private val service: PokemonService,
    private val mapper: PokemonServiceMapper
) :
    PokemonGetter {

    @Throws(IOException::class)
    override suspend fun getPokemon(identifier: String): Pokemon =
        mapper.mapPokemon(service.getPokemon(identifier))

    @Throws(IOException::class)
    override suspend fun getPokemonDetails(identifier: String): PokemonDetails {
        return mapper.mapPokemonDetails(service.getPokemonDetails(identifier))
    }

    @Throws(IOException::class)
    override suspend fun getPokemons(offset: Int?): List<Pokemon> {
        val pokemons = service.getPokemons(offset ?: 0)
        return mapper.mapPokemons(pokemons)
    }

    @Throws(IOException::class)
    override suspend fun getPokemonsByType(type: String): List<Pokemon> {
        val pokemons = service.getPokemonByType(type)
        return mapper.mapPokemonByType(pokemons)
    }

    @Throws(IOException::class)
    override suspend fun getPokemonEvolutionChain(identifier: String): PokemonEvolutionChain =
        PokemonEvolutionChain("", emptyList())

    @Throws(IOException::class)
    override suspend fun getAbilityDetails(abilityName: String): PokemonAbility {
        val abilityDetails = service.getAbilityDetails(abilityName)
        return  mapper.mapAbilities(abilityName, abilityDetails)
    }
}