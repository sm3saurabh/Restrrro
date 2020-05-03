package com.restaurantfinder.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class NonNullMediatorLiveData<T>: MediatorLiveData<T>() {

    fun observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
        this.observe(lifecycleOwner, Observer {
            it?.let(observer)
        })
    }
}

/**
 * @author Saurabh Mishra
 * We generally have a code for observing live data, where we do a null check before doing anything in the observe
 * To get rid of that, since I don't find it idiomatic. Use [nonNull] with a live data
 * With this, you will get an observe, that doesn't emit null values.
 */
fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator = NonNullMediatorLiveData<T>()

    mediator.addSource(this) {
        mediator.value = it
    }

    return mediator
}