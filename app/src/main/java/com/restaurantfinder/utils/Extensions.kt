package com.restaurantfinder.utils

import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun TextView.afterTextChanges(): Flow<Editable?> {
    return callbackFlow<Editable?> {
        checkMainThread()

        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                safeOffer(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }

        addTextChangedListener(textWatcher)

        awaitClose { removeTextChangedListener(textWatcher) }

    }.startImmediately {
        editableText
    }.conflate()
}

fun <T> SendChannel<T>.safeOffer(element: T) = !isClosedForSend && try {
    offer(element)
} catch (ex: Exception) {
    Log.e("SafeOffer", "Exception while offering element to the send channel", ex)
    false
}


fun checkMainThread() = check(Looper.myLooper() == Looper.getMainLooper()) {
    "This method needs to called from the main thread"
}

fun <T> Flow<T>.startImmediately(block: () -> T?): Flow<T> {
    return onStart {
        block()?.let { emit(it) }
    }
}

val Any.TAG: String
    get() {
        val name = this.javaClass.name

        return if (name.length > AppConstants.MAX_TAG_LENGTH) {
            name.substring(0, AppConstants.MAX_TAG_LENGTH)
        } else {
            name
        }
    }


// We check the context that the coroutine was launched in, has exception handler or not
// If not, we supply the default one
fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {

    val exceptionHandler = context[CoroutineExceptionHandler.Key]

    val callingContext = exceptionHandler?.let { context } ?: context + defaultCoroutineExceptionHandler

    launch(callingContext, block = block)
}

private val defaultCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("Default handler", "Exception in coroutine launch", throwable)
}