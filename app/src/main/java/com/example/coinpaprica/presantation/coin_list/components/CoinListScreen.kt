package com.example.coinpaprica.presantation.coin_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coinpaprica.common.Constant.COIN_ID
import com.example.coinpaprica.navigation.Screen
import com.example.coinpaprica.presantation.coin_list.CoinListEvent
import com.example.coinpaprica.presantation.coin_list.CoinListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = Color.Gray,
        topBar = {
            TopBar(onTextChange = {
                viewModel.setEvent(CoinListEvent.Search(it))
            })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.coins.filter { it.name.contains(state.search) }) { coin ->
                    CoinItem(coin = coin, onItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(COIN_ID, it.id)
                        navController.navigate(Screen.CoinDetailScreen.route)
                    })
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                )
            }
            if (!state.error.isNullOrEmpty()) {
                Text(text = state.error)
            }
        }
    }
}