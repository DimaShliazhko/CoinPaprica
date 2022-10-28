package com.example.coinpaprica.presantation.coin_list

import com.example.coinpaprica.base.State
import com.example.coinpaprica.domain.models.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val isDark: Boolean = false,
    val error: String = "",
    val coins: List<Coin> = emptyList(),
    val filterCoins: List<Coin> = emptyList(),
    val search: String = ""
) : State