package com.suongvong.interviewtest.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.suongvong.interviewtest.NewsApplication
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.suongvong.interviewtest.constants.SEARCH_PARAMS
import com.suongvong.interviewtest.dialog.DialogFactory
import com.suongvong.interviewtest.ui.category.CategoryFragment
import com.suongvong.interviewtest.ui.main.MainNavigator
import com.suongvong.interviewtest.ui.main.MainViewModel
import com.suongvong.interviewtest.ui.main.MainViewModelFactory
import com.suongvong.interviewtest.ui.search.SearchFragment

class MainActivity : BaseActivity<MainViewModel>(), MainNavigator,
     NavigationView.OnNavigationItemSelectedListener {

    private var toolBar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    private  lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController


    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory((application as NewsApplication).repository)
    }

    override fun setUpNavigator() = viewModel.setNavigator(this)


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupView() {
        toolBar = findViewById(R.id.toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView?.setNavigationItemSelectedListener(this)


//        supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment, HomeFragment())
//            .commit()
        setSupportActionBar(toolBar)
        setupActionBarDrawerToggle()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.homeFragment) {
//                supportActionBar?.setDisplayHomeAsUpEnabled(false)
//                toggle.isDrawerIndicatorEnabled = true
//                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//                toggle.syncState()
//            } else {
//                supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                toggle.isDrawerIndicatorEnabled = false
//                drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//                toggle.syncState()
//            }
//        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupActionBarDrawerToggle(){
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun setupData() {
        toolBar?.title = getString(R.string.app_name)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            toggle.onOptionsItemSelected(item) -> true
            item.itemId == android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, SearchFragment())
                    .commit()
            }

            R.id.nav_search -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.nav_host_fragment, DetailFragment())
//                    .commit()
                DialogFactory.openSearchNewsDialog(this){

//                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(it)
//                    navController.navigate(action)

                    val bundle = Bundle().apply {
                        putParcelable(SEARCH_PARAMS,it)
                    }
                    navController.navigate(R.id.searchFragment, bundle)
                }
            }

            R.id.nav_category_business,
            R.id.nav_category_entertainment,
            R.id.nav_category_health,
            R.id.nav_category_science,
            R.id.nav_category_sports,
            R.id.nav_category_technology->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, CategoryFragment())
                    .commit()
            }


        }
        drawerLayout?.closeDrawers()
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}