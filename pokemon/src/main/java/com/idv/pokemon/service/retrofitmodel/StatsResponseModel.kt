package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class StatsResponseModel {

    @SerializedName("base_stat")
    var baseStat: Int? = null

    @SerializedName("effort")
    var effort: Int? = null

    @SerializedName("stat")
    var stat: StatResponseModel? = null
}