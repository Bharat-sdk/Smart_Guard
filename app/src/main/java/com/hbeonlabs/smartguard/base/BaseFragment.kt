package com.hbeonlabs.smartguard.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : BaseViewModel, V : ViewBinding> : Fragment() {
    private lateinit var viewModel: T

    private lateinit var _viewBinding: V
    protected val binding get() = _viewBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init(container)
        return _viewBinding.root
    }

    private fun init(container: ViewGroup?) {
        _viewBinding =
            DataBindingUtil.inflate(layoutInflater, getLayoutResourceId(), container, false)
        viewModel = getViewModel()
        initView()
    }

    abstract fun getViewModel(): T
    abstract fun getLayoutResourceId(): Int



    open fun initView() {

    }

    fun snackBar(text: String) {
        Snackbar.make(
            requireView(),
            text,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun snackBar(@StringRes res: Int) {
        Snackbar.make(
            requireView(),
            res,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}