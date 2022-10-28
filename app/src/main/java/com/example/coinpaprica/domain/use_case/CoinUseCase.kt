package com.example.coinpaprica.domain.use_case

data class CoinUseCase(
    val getCoinsUseCase: GetCoinsUseCase,
    val getCoinDetailUseCase: GetCoinDetailUseCase
)