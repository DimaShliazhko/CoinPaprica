package com.example.coinpaprica.data.remote.model

import com.example.coinpaprica.domain.models.CoinDetail
import com.google.gson.annotations.SerializedName

data class CoinDetailEntity(
    val contract: String,
    val contracts: List<Contract>,
    val description: String?,
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val logo: String?,
    val message: String,
    val name: String,
    val parent: Parent,
    val platform: String,
    val rank: Int,
    val symbol: String,
    val tags: List<Tag>,
    val team: List<TeamMember>,
    val type: String,
    val whitepaper: Whitepaper
)

fun CoinDetailEntity.toCoinDetail(): CoinDetail {
    return CoinDetail(
        description = description,
        id = id,
        isActive = isActive,
        logo = logo,
        name = name,
        rank = rank,
        symbol = symbol,
        tags = tags,
        team = team,
        whitepaper = whitepaper
    )
}