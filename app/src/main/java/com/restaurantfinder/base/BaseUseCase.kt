package com.restaurantfinder.base

interface BaseUseCase<in InputParam, out OutPutParam> {

    suspend fun perform(input: InputParam): OutPutParam

}