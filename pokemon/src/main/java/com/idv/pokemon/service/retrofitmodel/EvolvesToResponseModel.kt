package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class EvolvesToResponseModel {

    @SerializedName("evolves_to")
    var evolvesTo : List<EvolvesToResponseModel>? = null

    @SerializedName("species")
    var specie : SpecieResponseModel? = null
}