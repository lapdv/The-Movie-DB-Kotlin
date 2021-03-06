package com.quanda.moviedb.ui.base.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.ui.base.navigator.BaseNavigator
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel

abstract class BaseDataBindFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : BaseFragment() {

    lateinit var binding: ViewBinding
    lateinit var viewModel: ViewModel
    lateinit var navigator: BaseNavigator

    abstract fun getLayoutId(): Int
    abstract fun initData()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseNavigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.apply {
            root.isClickable = true
            setLifecycleOwner(this@BaseDataBindFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }
}