package com.example.coinpaprica.presantation.coin_detail

import com.example.coinpaprica.base.Event

sealed class CoinDetailEvent() : Event {
    data class LoadCoinDetail(val coinId: String) : CoinDetailEvent()
}