package com.restaurantfinder.ui.dashboard.home


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.restaurantfinder.BuildConfig
import com.restaurantfinder.R
import com.restaurantfinder.base.BaseFragment
import com.restaurantfinder.databinding.FragmentHomeBinding
import com.restaurantfinder.databinding.LayoutFilterDialogBinding
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.usecases.RestaurantSearchUseCase
import com.restaurantfinder.utils.AppConstants
import com.restaurantfinder.utils.afterTextChanges
import com.restaurantfinder.utils.nonNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    // Base Fragment overrides --start
    override val viewModel: HomeViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
    // Base Fragment overrides --end

    private lateinit var locationClient: FusedLocationProviderClient

    private lateinit var homeController: HomeController

    private val job = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSearchBox()

        setupCurrentLocation(view.context)

        setupController()

        setupRecyclerView(view.context)

        startObserving()


    }

    private fun setupSearchBox() {
        with(binding.homeSearchBox) {

            searchBoxClear.setOnClickListener {

                val currentState = getCurrentHomeState()

                if (currentState == HomeState.SearchResult || currentState == HomeState.SearchEditing) {
                    searchBoxInput.setText("")
                    viewModel.updateHomeState(HomeState.Searching)
                }

            }

            searchBoxLeft.setOnClickListener {
                val currentState = getCurrentHomeState()

                if (currentState != HomeState.Idle) {
                    searchBoxInput.setText("")
                    viewModel.updateHomeState(HomeState.Idle)
                }
            }

            searchBoxInput.setOnFocusChangeListener { _, hasFocus ->
                val currentState = getCurrentHomeState()
                if (hasFocus && currentState == HomeState.Idle) {
                    viewModel.updateHomeState(HomeState.Searching)
                }
            }

            searchBoxInput.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                }
                false
            }

            searchBoxInput.afterTextChanges()
                .debounce(AppConstants.MAX_DEBOUNCE_DURATION)
                .map {
                    it?.toString().orEmpty()
                }.onEach { searchTerm ->

                    if (searchTerm.isNotEmpty()) {
                        viewModel.updateHomeState(HomeState.SearchEditing)
                        viewModel.restaurantSearch(searchTerm)
                    } else {
                        val currentState = getCurrentHomeState()

                        when (currentState) {
                            HomeState.SearchEditing, HomeState.SearchResult -> viewModel.updateHomeState(HomeState.Searching)
                        }
                    }
                }
                .launchIn(uiScope)

            searchBoxFilter.setOnClickListener {
                showFilterDialog()
            }
        }
    }

    private fun showFilterDialog() {

        viewModel.filterOption.value?.let { option ->
            val dataBinding = DataBindingUtil.inflate<LayoutFilterDialogBinding>(
                LayoutInflater.from(requireContext()), R.layout.layout_filter_dialog, null, false
            )

            setInitialFilter(option, dataBinding)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dataBinding.root)
                .setCancelable(false)
                .create()

            dialog.setCanceledOnTouchOutside(false)

            setupFilterDialogClickListeners(dataBinding, dialog)

            dialog.show()
        }

    }

    private fun setInitialFilter(option: RestaurantSearchUseCase.InputParam.FilterOption, dataBinding: LayoutFilterDialogBinding) {

        val isSortingByCost = option.sortOption == RestaurantSearchUseCase.InputParam.SortOptions.COST

        val isOrderAscending = option.orderOption == RestaurantSearchUseCase.InputParam.OrderOptions.ASC

        dataBinding.filterDialogSortCost.isSelected = isSortingByCost
        dataBinding.filterDialogSortRating.isSelected = !isSortingByCost

        dataBinding.filterDialogOrderAsc.isSelected = isOrderAscending
        dataBinding.filterDialogOrderDesc.isSelected = !isOrderAscending

    }

    private fun setupFilterDialogClickListeners(dataBinding: LayoutFilterDialogBinding, dialog: AlertDialog) {

        dataBinding.filterDialogOrderAsc.setOnClickListener {
            it.isSelected = true
            dataBinding.filterDialogOrderDesc.isSelected = false
        }

        dataBinding.filterDialogOrderDesc.setOnClickListener {
            it.isSelected = true
            dataBinding.filterDialogOrderAsc.isSelected = false
        }

        dataBinding.filterDialogSortCost.setOnClickListener {
            it.isSelected = true
            dataBinding.filterDialogSortRating.isSelected = false

        }

        dataBinding.filterDialogSortRating.setOnClickListener {
            it.isSelected = true
            dataBinding.filterDialogSortCost.isSelected = false
        }

        dataBinding.filterDialogSubmit.setOnClickListener {

            val sortingOption = if (dataBinding.filterDialogSortCost.isSelected) {
                RestaurantSearchUseCase.InputParam.SortOptions.COST
            } else {
                RestaurantSearchUseCase.InputParam.SortOptions.RATING
            }

            val orderOption = if (dataBinding.filterDialogOrderAsc.isSelected) {
                RestaurantSearchUseCase.InputParam.OrderOptions.ASC
            } else {
                RestaurantSearchUseCase.InputParam.OrderOptions.DESC
            }

            viewModel.updateFilterOption(
                RestaurantSearchUseCase.InputParam.FilterOption(
                    sortOption = sortingOption,
                    orderOption = orderOption
                )
            )

            dialog.dismiss()

        }

    }

    private fun getCurrentHomeState(): HomeState {
        return viewModel.data.value?.currentHomeState ?: HomeState.Idle
    }


    private fun setupCurrentLocation(context: Context) {

        locationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.locationSearch {
                suspendCoroutine { continuation ->
                    locationClient.lastLocation.addOnSuccessListener {
                        continuation.resume(it)
                    }
                }
            }
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                AppConstants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }


    }


    private fun showPermissionsRequired() {

    }

    private fun setupController() {
        homeController = HomeController(object : HomeController.EventHandler {
            override fun onRestaurantFavoriteToggled(restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant) {
                viewModel.updateFavorite(restaurant)
            }
        })

        homeController.isDebugLoggingEnabled = BuildConfig.DEBUG
    }

    private fun setupRecyclerView(context: Context) {
        with(binding.homeRecycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = homeController.adapter

        }
    }


    private fun startObserving() {
        viewModel.data.nonNull().observe(viewLifecycleOwner) { homeModel ->

            homeController.setData(homeModel)

            updateSearchBoxState(homeModel.currentHomeState)

        }

        viewModel.filterOption.nonNull().observe(viewLifecycleOwner) { option ->

            viewModel.data.value?.let { home ->
                viewModel.restaurantSearch(home.restaurantSearchResult.searchTerm)
            }

        }
    }

    private fun updateSearchBoxState(state: HomeState) {
        binding.homeSearchBox.isIdle = state == HomeState.Idle
        binding.homeSearchBox.isEditing = state == HomeState.SearchEditing || state == HomeState.SearchResult
        binding.homeSearchBox.shouldShowFilter = state == HomeState.Searching || state == HomeState.SearchResult

        if (state == HomeState.Idle) {
            binding.homeSearchBox.searchBoxInput.clearFocus()
            hideKeyboard()
        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == AppConstants.LOCATION_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.locationSearch {
                suspendCoroutine { continuation ->
                    locationClient.lastLocation.addOnSuccessListener {
                        continuation.resume(it)
                    }
                }
            }
        } else {
            showPermissionsRequired()
        }
    }


}
