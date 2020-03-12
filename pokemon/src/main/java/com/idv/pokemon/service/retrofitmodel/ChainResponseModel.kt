package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class ChainResponseModel {

    @SerializedName("evolves_to")
    var evolvesTo : EvolvesToResponseModel? = null
}