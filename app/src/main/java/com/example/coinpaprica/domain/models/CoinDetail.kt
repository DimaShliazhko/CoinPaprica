package com.example.coinpaprica.domain.models

import com.example.coinpaprica.data.remote.model.Tag
import com.example.coinpaprica.data.remote.model.TeamMember
import com.example.coinpaprica.data.remote.model.Whitepaper

data class CoinDetail(
    val description: String?,
    val id: String,
    val isActive: Boolean,
    val logo: String?,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<Tag>,
    val team: List<TeamMember>,
    val whitepaper: Whitepaper
)
