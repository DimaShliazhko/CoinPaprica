package com.example.coinpaprica.presantation.coin_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coinpaprica.common.Constant.COIN_ID
import com.example.coinpaprica.navigation.Screen
import com.example.coinpaprica.presantation.coin_list.CoinListEvent
import com.example.coinpaprica.presantation.coin_list.CoinListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = if (state.isDark) {
            Color.Gray
        } else {
            Color.LightGray
        },
        topBar = {
            TopBar(onTextChange = {
                viewModel.setEvent(CoinListEvent.Search(it))
            })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.coins.filter { it.name.contains(state.search) }) { coin ->
                    CoinItem(coin = coin, onItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(COIN_ID, it.id)
                        navController.navigate(Screen.CoinDetailScreen.route)
                    })
                }
            }

            if (!listState.isScrollInProgress) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .shadow(20.dp, RoundedCornerShape(20.dp)),
                    onClick = {
                        scope.launch { listState.animateScrollToItem(0) }
                    },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Red),
                    color = Color.DarkGray.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Icon(
                            imageVector = Icons.Default.Upgrade,
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "UP")
                    }
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