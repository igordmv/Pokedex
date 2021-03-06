package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonResponseModel {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("sprites")
    var sprites : SpritesResponseModel? = null

}