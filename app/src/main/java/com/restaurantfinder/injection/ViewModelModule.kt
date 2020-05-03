package com.restaurantfinder.injection

import com.restaurantfinder.ui.dashboard.DashboardViewModel
import com.restaurantfinder.ui.dashboard.favorites.FavoritesViewModel
import com.restaurantfinder.ui.dashboard.home.HomeViewModel
import com.restaurantfinder.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel() }

    viewModel { HomeViewModel(get(), get(), get(), get()) }

    viewModel { DashboardViewModel() }

    viewModel { FavoritesViewModel(get()) }


}