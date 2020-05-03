package com.restaurantfinder.utils


import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager

object UiUtils {

    fun animateConstraints(
        rootLayout: ConstraintLayout,
        finalConstraints: ConstraintSet,
        onAnimationStart: () -> Unit = {},
        onAnimationEnd: () -> Unit = {}
    ) {
        val transition = AutoTransition()
        transition.interpolator = LinearInterpolator()
        transition.duration = AppConstants.DEFAULT_ANIMATION_DURATION

        transition.addListener(object: TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                onAnimationEnd()
            }

            override fun onTransitionStart(transition: Transition) {
                onAnimationStart()
            }
        })

        TransitionManager.beginDelayedTransition(rootLayout, transition)
        finalConstraints.applyTo(rootLayout)

    }

}