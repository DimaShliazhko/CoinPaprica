package com.example.coinpaprica.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.coinpaprica.common.Constant.COIN_ID
import com.example.coinpaprica.presantation.coin_detail.components.CoinDetailScreen
import com.example.coinpaprica.presantation.coin_list.components.CoinListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.CoinListScreen.route
    ) {
        composable(
            route = Screen.CoinListScreen.route,
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            CoinListScreen(navController = navController)
        }

        composable(
            Screen.CoinDetailScreen.route,
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            navController.previousBackStackEntry?.savedStateHandle?.get<String>(COIN_ID)?.let {
                CoinDetailScreen(navController = navController, coinId = it)
            }
        }
    }
}