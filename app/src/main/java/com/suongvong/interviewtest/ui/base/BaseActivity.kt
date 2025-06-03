package com.suongvong.interviewtest.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getViewModelFactory(): ViewModelProvider.Factory
    abstract fun setUpNavigator()
    abstract fun setupView()
    abstract fun setupData()
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModelClass()]
        setUpNavigator()
        setupView()
        setupData()
    }
}