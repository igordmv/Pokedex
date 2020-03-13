package com.idv.pokemon.service.retrofitmodel

import com.google.gson.annotations.SerializedName

internal class PokemonEffectEntriesResponseModel {

    @SerializedName("effect_entries")
    var effectEntries : List<EffectsResponseModel>? = null
}