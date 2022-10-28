package com.example.coinpaprica.domain.repository

import com.example.coinpaprica.data.remote.model.CoinDetailEntity
import com.example.coinpaprica.data.remote.model.CoinEntity

interface CoinRepository {
    suspend fun getCoins(): List<CoinEntity>
    suspend fun getCoinById(coinId: String): CoinDetailEntity
}