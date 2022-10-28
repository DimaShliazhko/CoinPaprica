package com.example.coinpaprica.presantation.coin_list

import com.example.coinpaprica.base.Event

sealed class CoinListEvent() : Event {
    data class Search(val text: String?) : CoinListEvent()
}