package com.example.coinpaprica.domain.use_case

import com.example.coinpaprica.common.Results
import com.example.coinpaprica.data.remote.model.toCoin
import com.example.coinpaprica.domain.models.Coin
import com.example.coinpaprica.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
     operator fun invoke(): Flow<Results<List<Coin>>> = flow {

        try {
            emit(Results.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Results.Success(data = coins))
        } catch (e: HttpException) {
            emit(Results.Error(messages = e.localizedMessage))
        } catch (e: IOException) {
            emit(Results.Error(messages = e.localizedMessage))
        }
    }
}