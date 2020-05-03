package com.restaurantfinder.network.utils

import retrofit2.Response
import java.lang.Exception

sealed class SafeResult<out T> {

    class Success<T>(val data: T?): SafeResult<T>()
    class Failure(val exception: Exception): SafeResult<Nothing>()

}


// Having this method reduces boiler plate that we were bound to have everywhere else for error checking
suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>
): SafeResult<T> {

    val callResponse = call.invoke()

    return if (callResponse.isSuccessful) {
        SafeResult.Success(callResponse.body())
    } else {
        val apiExceptionMessage = "Error code -> ${callResponse.code()}, Error message -> ${callResponse.message()}"
        SafeResult.Failure(Exception(apiExceptionMessage))
    }

}