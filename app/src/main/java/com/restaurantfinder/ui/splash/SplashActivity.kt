package com.restaurantfinder.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.restaurantfinder.R
import com.restaurantfinder.base.BaseActivity
import com.restaurantfinder.databinding.ActivitySplashBinding
import com.restaurantfinder.ui.dashboard.DashboardActivity
import com.restaurantfinder.utils.UiUtils
import com.restaurantfinder.utils.nonNull
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {


    // Base activity overrides --start
    override val viewModel: SplashViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
    // Base activity overrides --end


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAnimations()

        startObserving()
    }

    private fun setupAnimations() {
        viewModel.startAnimationWithDelay()
    }

    private fun startObserving() {
        viewModel.startAnimation.nonNull().observe(this) { shouldAnimate ->

            if (shouldAnimate) {
                startSplashAnimation {
                    viewModel.animationCompleted()
                }
            }
        }

        viewModel.startNextActivity.nonNull().observe(this) { start ->
            if (start) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }

    private fun startSplashAnimation(onAnimationEnd: () -> Unit) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.splashRoot)
        constraintSet.setGuidelinePercent(binding.splashTopGuideline.id, 0.25f)
        UiUtils.animateConstraints(
            binding.splashRoot, constraintSet, onAnimationEnd = onAnimationEnd
        )
    }
}
