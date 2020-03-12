package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonEvolutionChainUrlResponseModel {

    @SerializedName("evolution_chain")
    var evolutionChain : EvolutionChainResponseModel? = null
}