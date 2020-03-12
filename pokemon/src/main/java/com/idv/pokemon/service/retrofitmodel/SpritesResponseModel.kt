package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class SpritesResponseModel {

    @SerializedName("front_default")
    var front : String? = null

    @SerializedName("front_female")
    var frontFemale : String? = null

    @SerializedName("front_shiny")
    var frontShiny : String? = null

    @SerializedName("front_shiny_female")
    var frontShinyFemale : String? = null

    @SerializedName("back_default")
    var back : String? = null

    @SerializedName("back_female")
    var backFemale : String? = null

    @SerializedName("back_shiny")
    var backShiny : String? = null

    @SerializedName("back_shiny_female")
    var backShinyFemale : String? = null

}