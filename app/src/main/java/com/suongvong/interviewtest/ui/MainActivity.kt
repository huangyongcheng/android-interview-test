package com.suongvong.interviewtest.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.suongvong.interviewtest.constants.CATEGORY_POSITION
import com.suongvong.interviewtest.constants.SEARCH_PARAMS
import com.suongvong.interviewtest.dialog.DialogFactory

class MainActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private var toolBar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupView() {
        toolBar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView?.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolBar)
        setupNavigationController()
    }

    private fun setupNavigationController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.categoryFragment
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun setupData() {
        toolBar?.title = getString(R.string.app_name)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.homeFragment)
            }
            R.id.nav_search -> {
                DialogFactory.openSearchNewsDialog(this) { searchParams ->
                    val bundle = Bundle().apply {
                        putParcelable(SEARCH_PARAMS, searchParams)
                    }
                    navController.navigate(R.id.searchFragment, bundle)
                }
            }
            R.id.nav_category_business,
            R.id.nav_category_entertainment,
            R.id.nav_category_health,
            R.id.nav_category_science,
            R.id.nav_category_sports,
            R.id.nav_category_technology -> {
                val categoryIndex = when (item.itemId) {
                    R.id.nav_category_business -> 0
                    R.id.nav_category_entertainment -> 1
                    R.id.nav_category_health -> 2
                    R.id.nav_category_science -> 3
                    R.id.nav_category_sports -> 4
                    R.id.nav_category_technology -> 5
                    else -> -1
                }
                if (categoryIndex != -1) {
                    val bundle = Bundle().apply {
                        putInt(CATEGORY_POSITION, categoryIndex)
                    }
                    navController.navigate(R.id.categoryFragment, bundle)
                }
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
