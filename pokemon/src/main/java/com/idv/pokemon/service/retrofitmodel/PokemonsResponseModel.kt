package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonsResponseModel {

    @SerializedName("count")
    var count : Int? = null

    @SerializedName("results")
    var result : List<ResultResponseModel>? = emptyList()
}