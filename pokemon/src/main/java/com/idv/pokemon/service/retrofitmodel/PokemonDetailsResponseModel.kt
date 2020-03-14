package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonDetailsResponseModel {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("id")
    var id : Int? = null

    @SerializedName("weight")
    var weight: Int? = null

    @SerializedName("height")
    var height: Int? = null

    @SerializedName("abilities")
    var abilities: List<AbilitiesResponseModel>? = emptyList()

    @SerializedName("forms")
    var forms: List<FormResponseModel>? = emptyList()

    @SerializedName("types")
    var types : List<TypesResponseModel>? = emptyList()

    @SerializedName("stats")
    var stats : List<StatsResponseModel>? = emptyList()

    @SerializedName("sprites")
    var sprites : SpritesResponseModel? = null

}
