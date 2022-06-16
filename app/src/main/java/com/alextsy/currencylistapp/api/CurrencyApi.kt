package com.alextsy.currencylistapp.api

import com.alextsy.currencylistapp.data.CurrencyItem
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    companion object {
        const val BASE_URL = "https://api.nomics.com/v1/"
    }

    @GET("currencies/ticker")
    suspend fun getCurrencies(
        @Query("key") key: String,
        @Query("convert") convert: String,
        @Query("page") page: Int,
        @Query("per-page") perPage: Int,
    ): List<CurrencyItem>
}