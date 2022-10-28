package com.example.coinpaprica.data

import com.example.coinpaprica.data.remote.model.CoinDetailEntity
import com.example.coinpaprica.data.remote.model.CoinEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {
    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinEntity>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailEntity
}