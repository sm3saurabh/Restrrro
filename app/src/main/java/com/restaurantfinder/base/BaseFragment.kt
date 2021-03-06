package com.restaurantfinder.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<VM: ViewModel, B: ViewDataBinding>: Fragment() {

    abstract val viewModel: VM

    abstract fun getLayoutId(): Int

    private var _binding: B? = null

    // We won't interact with or access binding after the view is destroyed
    // It's safe to assume that _binding won't when accesssing the following member
    val binding: B
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideKeyboard() {
        val hostActivity = activity
        hostActivity?.currentFocus?.let {
            val imm = hostActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}