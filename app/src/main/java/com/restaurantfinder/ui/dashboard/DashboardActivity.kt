package com.restaurantfinder.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.restaurantfinder.R
import com.restaurantfinder.base.BaseActivity
import com.restaurantfinder.databinding.ActivityDashboardBinding
import com.restaurantfinder.ui.dashboard.favorites.FavoritesFragment
import com.restaurantfinder.ui.dashboard.home.HomeFragment
import com.restaurantfinder.utils.nonNull
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<DashboardViewModel, ActivityDashboardBinding>() {

    // Base Activity overrides --start
    override val viewModel: DashboardViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard
    }
    // Base Activity overrides --end


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupClickListeners()

        startObserving()

    }

    private fun setupClickListeners() {

        binding.dashboardBottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_nav_menu_home -> {
                    viewModel.switchDashboardState(DashboardViewModel.DashboardState.Home)
                }
                R.id.bottom_nav_menu_fav -> {
                    viewModel.switchDashboardState(DashboardViewModel.DashboardState.Favorites)
                }
            }

            true
        }
    }

    private fun startObserving() {
        viewModel.currentState.nonNull().observe(this) { state ->
            switchFragments(state)
        }
    }

    private fun switchFragments(state: DashboardViewModel.DashboardState) {

        when (state) {
            DashboardViewModel.DashboardState.Home -> toggleFragment(HomeFragment())
            DashboardViewModel.DashboardState.Favorites -> toggleFragment(FavoritesFragment())
        }

    }

    private fun toggleFragment(show: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dashboard_fragment_container, show, show.tag).commit()
    }


}
