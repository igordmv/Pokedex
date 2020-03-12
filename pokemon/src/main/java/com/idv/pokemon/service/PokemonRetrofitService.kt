package com.idv.pokemon.service

import com.google.gson.Gson
import com.idv.pokemon.service.retrofitmodel.*
import com.idv.pokemon.service.retrofitmodel.PokemonDetailsRespondeModel
import com.idv.pokemon.service.retrofitmodel.PokemonEvolutionChainResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonEvolutionChainUrlResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonResponseModel
import com.idv.pokemon.service.retrofitmodel.PokemonsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

internal interface PokemonRetrofitService {

    @GET()
    fun getPokemon(@Url url: String): Call<PokemonResponseModel>

    @GET("/api/v2/pokemon/")
    fun getPokemons(@Query ("offset") offset: Int, @Query("limit") limit : Int = 20): Call<PokemonsResponseModel>

    @GET()
    fun getPokemonDetails(@Url url: String): Call<PokemonDetailsRespondeModel>

    @GET()
    fun getEvolutionChainUrl(@Url url: String): Call<PokemonEvolutionChainUrlResponseModel>

    @GET()
    fun getEvolutionChain(@Url url: String): Call<Gson>
}