package com.idv.pokemon_entity

data class PokemonEvolutionChain (
    val rootPokemonName : String,
    val pokemonEvolutionList: List<Pokemon>
)