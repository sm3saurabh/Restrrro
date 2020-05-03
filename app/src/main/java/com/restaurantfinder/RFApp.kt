package com.restaurantfinder

import android.app.Application
import com.restaurantfinder.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RFApp : Application() {


    override fun onCreate() {
        super.onCreate()

        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {

            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    databaseModule,
                    viewModelModule,
                    useCaseModule
                )
            )

            androidContext(this@RFApp)
            androidLogger()

        }

    }
}