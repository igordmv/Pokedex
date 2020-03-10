package com.idv.pokemon.usecases.get

import com.idv.pokemon.service.PokemonService
import com.idv.pokemon.service.mapper.PokemonServiceMapper
import com.idv.pokemon_entity.Pokemon
import com.idv.pokemon_entity.PokemonEvolutionChain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    override suspend fun getPokemons(offset : Int?): List<Pokemon> {
        val pokemons = service.getPokemons(offset?: 0)
        val pokemonList = mutableListOf<Pokemon>()
        var count = 0
        pokemons.result?.forEach {
            it.name?.let { pokemonName ->
                pokemonList.add(count, mapper.mapPokemon(service.getPokemon(pokemonName)))
                count++
            }
        }
        return pokemonList
    }

    @Throws(IOException::class)
    override suspend fun getPokemonEvolutionChain(identifier: String): PokemonEvolutionChain =
        PokemonEvolutionChain("", emptyList())
}