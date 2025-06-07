package com.suongvong.interviewtest.ui.base

import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suongvong.interviewtest.utils.ShimmerHelper

abstract class BaseFragment<V : BaseViewModel<*>> : Fragment() {

    protected lateinit var viewModel: V
    private lateinit var rootView: View

    protected abstract fun getLayoutId(): Int
    protected abstract fun setupViewModel(): V
    protected abstract fun setupData(savedInstanceState: Bundle?)
    protected open fun setupView() {}
    protected open fun getArgumentIntent() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgumentIntent()
        viewModel = setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(getLayoutId(), container, false)
        setupView()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData(savedInstanceState)
    }

    protected open fun findViewById(id: Int): View? = rootView.findViewById(id)

    protected open fun startShimmerByViews(vararg views: View?) {
        activity?.runOnUiThread {
            views.forEach { it?.apply {
                visibility = View.VISIBLE
                ShimmerHelper.findAndStartShimmer(this)
            }}
        }
    }

    protected open fun stopShimmerByViews(vararg views: View?) {
        activity?.runOnUiThread {
            views.forEach { it?.apply {
                visibility = View.GONE
                ShimmerHelper.findAndStopShimmer(this)
            }}
        }
    }
}
