package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class AbilitiesResponseModel {

    @SerializedName("ability")
    var ability : AbilityResponseModel? = null

    @SerializedName("is_hidden")
    var isHidden : Boolean? = null

    @SerializedName("slot")
    var slot : Int? = null
}