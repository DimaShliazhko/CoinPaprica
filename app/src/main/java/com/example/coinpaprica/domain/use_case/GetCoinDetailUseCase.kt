package com.example.coinpaprica.domain.use_case

import com.example.coinpaprica.common.Results
import com.example.coinpaprica.data.remote.model.toCoinDetail
import com.example.coinpaprica.domain.models.CoinDetail
import com.example.coinpaprica.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Results<CoinDetail>> = flow {
        try {
            emit(Results.Loading())
            val coins = repository.getCoinById(coinId).toCoinDetail()
            emit(Results.Success(data = coins))
        } catch (e: HttpException) {
            emit(Results.Error(messages = e.localizedMessage))
        } catch (e: IOException) {
            emit(Results.Error(messages = e.localizedMessage))
        }
    }
}