package com.restaurantfinder.ui.dashboard.favorites


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurantfinder.BuildConfig
import com.restaurantfinder.R
import com.restaurantfinder.base.BaseFragment
import com.restaurantfinder.databinding.FragmentFavoritesBinding
import com.restaurantfinder.utils.nonNull
import org.koin.android.viewmodel.ext.android.viewModel


class FavoritesFragment : BaseFragment<FavoritesViewModel, FragmentFavoritesBinding>() {

    // Base Fragment overrides --start
    override val viewModel: FavoritesViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorites
    }
    // Base Fragment overrides --end

    private lateinit var favoriteController: FavoriteController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupController()

        setupRecycler(view.context)

        startObserving()

    }


    private fun setupController() {
        favoriteController = FavoriteController()
        favoriteController.isDebugLoggingEnabled = BuildConfig.DEBUG
    }

    private fun setupRecycler(context: Context) {
        with(binding.favoritesRecycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteController.adapter
        }
    }


    private fun startObserving() {
        viewModel.getAllFavorites().nonNull().observe(viewLifecycleOwner) {
            favoriteController.setData(it)
        }
    }




}
