package com.example.coinpaprica.presantation.coin_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.coinpaprica.presantation.coin_detail.CoinDetailEvent
import com.example.coinpaprica.presantation.coin_detail.CoinDetailViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoinDetailScreen(
    navController: NavController,
    coinId: String,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = coinId) {
        viewModel.setEvent(CoinDetailEvent.LoadCoinDetail(coinId))
    }

    Box(modifier = Modifier.fillMaxSize()) {

        state.coinDetail?.let { coin ->
            coin.logo?.let {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Center)
                     //   .rotate(degrees = state.sides)
                        .graphicsLayer {
                            rotationX =  state.upDown
                            rotationY =  state.sides
                            cameraDistance = 12f * density
                        },
                    alpha = 0.5f,
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(coin.logo),
                    contentDescription = "img_logo"
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.weight(8f)
                        )
                        Text(
                            text = if (coin.isActive) "active" else "inactive",
                            color = if (coin.isActive) Color.Green else Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .align(CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = coin.description ?: "",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    coin.logo?.let {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(coin.logo),
                            contentDescription = "img_logo"
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        coin.tags.forEach { tag ->
                            CoinTag(tag = tag.name)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Team members",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
                items(coin.team) { teamMember ->
                    TeamListItem(
                        teamMember = teamMember,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Divider()
                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}