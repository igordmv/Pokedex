package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class EffectsResponseModel {

    @SerializedName("effect")
    var effect : String? = null

    @SerializedName("short_effect")
    var shortEffect : String? = null
}