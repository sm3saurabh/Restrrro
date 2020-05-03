package com.restaurantfinder.injection

import com.restaurantfinder.database.RFDatabase
import com.restaurantfinder.network.RetrofitHelper
import com.restaurantfinder.network.utils.NetworkUtils
import com.restaurantfinder.repository.FavoriteRepository
import com.restaurantfinder.repository.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// In this file, we will specify modules that are scoped through the application

val networkModule = module {

    // Expose an api service instance, this will be scoped singleton throughout the application
    // We can also write this as [RetrofitHelper.zomatoSearchApi(methodChain)]
    // But instead, we will leverage the koin's object graph
    single { RetrofitHelper.zomatoSearchApi(get()) }

    single { NetworkUtils.createRetrofit(get()) }

    single { NetworkUtils.createOkHttpClient(get()) }

    single { NetworkUtils.createHttpInterceptor() }
}

val repositoryModule = module {
    single { SearchRepository(get()) }

    single { FavoriteRepository(get()) }
}

val databaseModule = module {

    single { RFDatabase.getDatabase(androidContext()) }

}



