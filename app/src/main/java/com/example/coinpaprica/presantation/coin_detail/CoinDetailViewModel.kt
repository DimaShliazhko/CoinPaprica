package com.example.coinpaprica.presantation.coin_detail

import androidx.lifecycle.viewModelScope
import com.example.coinpaprica.base.BaseViewModel
import com.example.coinpaprica.common.Results
import com.example.coinpaprica.data.sensor.AccelerometerSensor
import com.example.coinpaprica.data.sensor.MeasurableSensor
import com.example.coinpaprica.domain.use_case.CoinUseCase
import com.example.coinpaprica.presantation.coin_list.CoinListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val useCase: CoinUseCase,
    private val accelerometerSensor: AccelerometerSensor
) : BaseViewModel<CoinDetailEvent, CoinDetailState, CoinDetailAction>() {

    init {
        initSensor()
    }

    private fun initSensor() {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            val sides = values[0]
            val upDown = values[1]
            _state.value = _state.value.copy(
                sides = sides * 10f,
                upDown = upDown * 10f
            )
        }
    }

    override fun createInitialState() = CoinDetailState()

    override fun handleEvent(event: CoinDetailEvent) {
        when (event) {
            is CoinDetailEvent.LoadCoinDetail -> {
                getCoinDetail(event.coinId)
            }
        }
    }

    private fun getCoinDetail(coinId: String) {

        useCase.getCoinDetailUseCase(coinId).onEach { result ->
            when (result) {
                is Results.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.messages ?: "Ups.."
                    )
                }
                is Results.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }

                is Results.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        coinDetail = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
