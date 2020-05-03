package com.restaurantfinder.injection

import com.restaurantfinder.usecases.*
import org.koin.dsl.module

val useCaseModule = module {

    // Factory in koin is like prototype scope in spring or no scope in dagger
    // It will generate a new instance every time this object is requested
    factory { RestaurantSearchUseCase(get(), get()) }

    factory { LocationSearchUseCase(get()) }

    factory { AddFavoriteRestaurantUseCase(get()) }

    factory { DeleteFavoriteRestaurantUseCase(get()) }

    factory { GetAllFavoriteRestaurantsUseCase(get()) }
}