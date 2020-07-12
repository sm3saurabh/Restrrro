package com.restaurantfinder.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM: ViewModel, B: ViewDataBinding>: AppCompatActivity() {

    abstract val viewModel: VM

    abstract fun getLayoutId(): Int

    private var _binding: B? = null

    // We won't interact with or access binding after the view is destroyed
    // It's safe to assume that _binding won't when accesssing the following member
    val binding: B
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, getLayoutId())

    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}