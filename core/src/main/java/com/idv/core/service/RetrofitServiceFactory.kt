package com.idv.core.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

object RetrofitServiceFactory : ServiceFactory {
    private val httpClient by lazy { OkHttpClient() }
    private val converter by lazy { GsonConverterFactory.create() }
    private val executor by lazy {
        Executor { command ->
            GlobalScope.launch(Dispatchers.IO) {
                command.run()
            }
        }
    }

    override fun <T> make(baseURL: String, serviceClass: Class<T>): T = Retrofit
        .Builder()
        .baseUrl(baseURL)
        .addConverterFactory(converter)
        .client(httpClient)
        .callbackExecutor(executor)
        .build()
        .create(serviceClass)
}