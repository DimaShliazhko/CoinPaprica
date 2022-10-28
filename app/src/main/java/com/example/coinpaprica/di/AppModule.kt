package com.example.coinpaprica.di

import com.example.coinpaprica.common.Constant.BASE_URL
import com.example.coinpaprica.data.CoinApi
import com.example.coinpaprica.data.repository.CoinRepositoryImpl
import com.example.coinpaprica.domain.repository.CoinRepository
import com.example.coinpaprica.domain.use_case.CoinUseCase
import com.example.coinpaprica.domain.use_case.GetCoinDetailUseCase
import com.example.coinpaprica.domain.use_case.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun privideCoinApi(): CoinApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(coinApi: CoinApi): CoinRepository {
        return CoinRepositoryImpl(coinApi)
    }

    @Provides
    @Singleton
    fun provideCoinUseCase(coinRepository: CoinRepository): CoinUseCase {
        return CoinUseCase(
            getCoinDetailUseCase = GetCoinDetailUseCase(coinRepository),
            getCoinsUseCase = GetCoinsUseCase(coinRepository),
        )
    }
}