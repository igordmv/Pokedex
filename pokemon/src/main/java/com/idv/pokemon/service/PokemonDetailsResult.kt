package com.idv.pokemon.service

import com.idv.pokemon.service.retrofitmodel.PokemonDetailsRespondeModel
import com.idv.pokemon.service.retrofitmodel.PokemonEvolutionChainResponseModel

internal data class PokemonDetailsResult(
    val pokemonDetails: PokemonDetailsRespondeModel,
    val evolutionChain: PokemonEvolutionChainResponseModel?
)