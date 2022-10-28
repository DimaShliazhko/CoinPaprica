package com.example.coinpaprica.presantation.coin_detail

import com.example.coinpaprica.base.State
import com.example.coinpaprica.domain.models.Coin
import com.example.coinpaprica.domain.models.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val error: String = "",
    val coinDetail: CoinDetail? = null,
    val sides: Float = 0f,
    val upDown: Float = 0f,
) : State