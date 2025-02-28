package com.base.mastercardgatewaytest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

object RetrofitClient {

    private const val BASE_URL = "https://mtf.gateway.mastercard.com/"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService: MastercardApiService = retrofit.create(MastercardApiService::class.java)
}
