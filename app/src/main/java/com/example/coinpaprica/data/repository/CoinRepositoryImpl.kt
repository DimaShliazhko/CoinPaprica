package com.example.coinpaprica.data.repository

import com.example.coinpaprica.data.CoinApi
import com.example.coinpaprica.data.remote.model.CoinDetailEntity
import com.example.coinpaprica.data.remote.model.CoinEntity
import com.example.coinpaprica.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {
    override suspend fun getCoins(): List<CoinEntity> {
        return coinApi.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailEntity {
        return coinApi.getCoinById(coinId)
    }
}