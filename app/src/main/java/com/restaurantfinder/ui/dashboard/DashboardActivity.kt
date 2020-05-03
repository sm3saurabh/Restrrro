package com.restaurantfinder.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.restaurantfinder.R
import com.restaurantfinder.base.BaseActivity
import com.restaurantfinder.databinding.ActivityDashboardBinding
import com.restaurantfinder.ui.dashboard.favorites.FavoritesFragment
import com.restaurantfinder.ui.dashboard.home.HomeFragment
import com.restaurantfinder.utils.TAG
import com.restaurantfinder.utils.nonNull
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<DashboardViewModel, ActivityDashboardBinding>() {

    // Base Activity overrides --start
    override val viewModel: DashboardViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard
    }
    // Base Activity overrides --end

    private val homeFragment by lazy { HomeFragment() }
    private val favoritesFragment by lazy { FavoritesFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFragments()

        setupClickListeners()

        startObserving()

    }

    private fun setupFragments() {
        supportFragmentManager.beginTransaction().add(R.id.dashboard_fragment_container, homeFragment, homeFragment.TAG).commit()
        supportFragmentManager.beginTransaction().add(R.id.dashboard_fragment_container, favoritesFragment, favoritesFragment.TAG).commit()
    }

    private fun setupClickListeners() {
        binding.dashboardNavHomeContainer.setOnClickListener {
            viewModel.switchDashboardState(DashboardViewModel.DashboardState.Home)
        }

        binding.dashboardNavFavoriteContainer.setOnClickListener {
            viewModel.switchDashboardState(DashboardViewModel.DashboardState.Favorites)
        }
    }

    private fun startObserving() {
        viewModel.currentState.nonNull().observe(this) { state ->
            binding.shouldShowHome = state == DashboardViewModel.DashboardState.Home

            switchFragments(state)
        }
    }

    private fun switchFragments(state: DashboardViewModel.DashboardState) {

        when (state) {
            DashboardViewModel.DashboardState.Home -> toggleFragment(homeFragment, favoritesFragment)
            DashboardViewModel.DashboardState.Favorites -> toggleFragment(favoritesFragment, homeFragment)
        }

    }

    private fun toggleFragment(show: Fragment, hide: Fragment) {
        supportFragmentManager.beginTransaction().show(show).hide(hide).commit()
    }


}
