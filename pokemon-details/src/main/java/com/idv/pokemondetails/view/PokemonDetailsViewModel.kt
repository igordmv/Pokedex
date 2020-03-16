package com.idv.pokemondetails.view

internal data class PokemonDetailsViewModel(
    val id : Int?,
    val name : String?,
    val height : Int?,
    val weight : Int?,
    val defaultImage : String?,
    val images : List<String>?,
    val types : List<String>?,
    val speed : Int?,
    val specialDefence : Int?,
    val defence : Int?,
    val attack : Int?,
    val specialAttack : Int?,
    val hitPoints : Int?,
    val abilities : List<String>?,
    val evolutionChain : List<PokemonEvolutionChainViewModel>? = null
)
