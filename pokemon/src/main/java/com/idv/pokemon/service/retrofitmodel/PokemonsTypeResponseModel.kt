package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonsTypeResponseModel {
    @SerializedName("pokemon")
    var pokemonList : List<PokemonTypeResponseModel>? = null

}