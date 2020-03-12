package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonEvolutionChainResponseModel {

    @SerializedName("chain")
    var chain : ChainResponseModel? = null
}