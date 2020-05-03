package com.restaurantfinder.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurantfinder.utils.AppConstants
import com.restaurantfinder.utils.safeLaunch
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean> = _startAnimation

    private val _startNextActivity = MutableLiveData<Boolean>()
    val startNextActivity: LiveData<Boolean> = _startNextActivity

    fun startAnimationWithDelay() {

        viewModelScope.safeLaunch {
            delay(AppConstants.DEFAULT_ANIMATION_DELAY)
            _startAnimation.value = true
        }
    }

    fun animationCompleted() {
        viewModelScope.safeLaunch {
            delay(AppConstants.AFTER_ANIMATION_DELAY)
            _startNextActivity.value = true
        }
    }

}