package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class SpecieResponseModel {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("url")
    var url : String? = null
}