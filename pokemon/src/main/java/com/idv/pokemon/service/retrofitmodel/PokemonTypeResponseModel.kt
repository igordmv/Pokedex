package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonTypeResponseModel {

    @SerializedName("pokemon")
    var pokemon : PokemonWithTypeResponseModel? = null
}