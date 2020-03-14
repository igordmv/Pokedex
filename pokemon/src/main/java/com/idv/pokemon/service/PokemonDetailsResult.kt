package com.idv.pokemon.service

import com.idv.pokemon.service.retrofitmodel.PokemonDetailsResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonEvolutionChainResponseModel

internal data class PokemonDetailsResult(
    val pokemonDetails: PokemonDetailsResponseModel,
    val evolutionChain: PokemonEvolutionChainResponseModel?
)