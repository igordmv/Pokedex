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
    override suspend fun getPokemon(identifier: String): Pokemon =
        mapper.mapPokemon(service.getPokemon(identifier))

    @Throws(IOException::class)
    override suspend fun getPokemonEvolutionChain(identifier: String): PokemonEvolutionChain =
        PokemonEvolutionChain("", emptyList())
}