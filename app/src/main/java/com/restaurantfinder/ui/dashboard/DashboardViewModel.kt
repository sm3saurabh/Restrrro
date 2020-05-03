package com.restaurantfinder.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel: ViewModel() {


    private val _currentState = MutableLiveData<DashboardState>().apply { value = DashboardState.Home }
    val currentState: LiveData<DashboardState> = _currentState


    fun switchDashboardState(newState: DashboardState) {
        _currentState.value?.let { state ->
            if (newState != state) {
                _currentState.value = newState
            }
        }
    }


    sealed class DashboardState {
        object Home: DashboardState()
        object Favorites: DashboardState()
    }
}