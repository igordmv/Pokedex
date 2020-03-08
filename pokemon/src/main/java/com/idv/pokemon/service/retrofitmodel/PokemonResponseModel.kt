package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonResponseModel {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("order")
    var order : Int? = null

    @SerializedName("weight")
    var weight: Int? = null

    @SerializedName("base_experience")
    var baseExperience: Int? = null

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