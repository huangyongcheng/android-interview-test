package com.suongvong.interviewtest.ui.base


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : BaseViewModel<*>> : Fragment() {
    protected abstract fun getLayoutId(): Int
    private lateinit var rootView: View
    abstract fun setupData(savedInstanceState: Bundle?)
    lateinit var viewModel: V
    abstract fun setupViewModel(): V
    open fun setupView() {}
    open fun getArgumentIntent() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgumentIntent()
        viewModel = setupViewModel()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        setupView()
        return rootView
    }

    open fun findViewById(id: Int): View? {
        return rootView.findViewById(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData(savedInstanceState)
    }




}



