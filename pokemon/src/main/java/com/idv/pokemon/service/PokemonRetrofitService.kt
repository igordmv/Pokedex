package com.idv.pokemon.service

import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

internal interface PokemonRetrofitService {

    @GET()
    fun getPokemon(@Url url: String): Call<PokemonResponseModel>

}