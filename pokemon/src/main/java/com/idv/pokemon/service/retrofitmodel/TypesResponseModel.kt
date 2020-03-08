package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class TypesResponseModel {

    @SerializedName("type")
    var type : TypeResponseModel? = null

    @SerializedName("slot")
    var slot : Int? = null
}