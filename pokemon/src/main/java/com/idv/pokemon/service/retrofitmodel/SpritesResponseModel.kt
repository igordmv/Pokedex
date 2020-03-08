package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class SpritesResponseModel {

    @SerializedName("front_default")
    var front : String? = null

    @SerializedName("front_shiny")
    var frontShiny : String? = null

}