package com.example.coinpaprica.presantation.coin_list

import androidx.lifecycle.viewModelScope
import com.example.coinpaprica.base.BaseViewModel
import com.example.coinpaprica.common.Results

import com.example.coinpaprica.data.sensor.MeasurableSensor
import com.example.coinpaprica.domain.use_case.CoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val useCase: CoinUseCase,
    private val lightSensor: MeasurableSensor
) : BaseViewModel<CoinListEvent, CoinListState, CoinListAction>() {

    init {
        initSensor()
        getCoins()
    }

    private fun initSensor() {
        lightSensor.startListening()
        lightSensor.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            _state.value = _state.value.copy(
                isDark = lux < 1000f
            )
        }
    }

    override fun createInitialState() = CoinListState()

    override fun handleEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.Search -> {
                search(event.text)
            }
        }
    }

    private fun search(text: String?) {

        _state.value = _state.value.copy(
            search = text ?: ""
        )
    }

    fun getCoins() {

        useCase.getCoinsUseCase().onEach { result ->
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
                        coins = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
