package com.suongvong.interviewtest.ui.home

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.suongvong.interviewtest.AuthenticatorApplication
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.TokenAdapter
import com.suongvong.interviewtest.dialog.AddKeyBottomSheetDialog
import com.suongvong.interviewtest.dialog.DialogFactory
import com.suongvong.interviewtest.extentions.copyToClipboard
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.ui.base.BaseActivity
import com.suongvong.interviewtest.ui.manual.ManualActivity
import com.suongvong.interviewtest.ui.scan.ScanQRActivity
import com.google.android.material.navigation.NavigationView
import com.suongvong.interviewtest.ui.detail.DetailFragment
import com.suongvong.interviewtest.ui.main.MainNavigator
import com.suongvong.interviewtest.ui.main.MainViewModel
import com.suongvong.interviewtest.ui.main.MainViewModelFactory

class MainActivity2 : BaseActivity<MainViewModel>(), TokenAdapter.ItemListener, View.OnClickListener, MainNavigator,
    AddKeyBottomSheetDialog.AddKeyBottomSheetDialogListener , NavigationView.OnNavigationItemSelectedListener {

    private var toolBar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    private  lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController


    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory((application as AuthenticatorApplication).repository)
    }

    override fun setUpNavigator() = viewModel.setNavigator(this)


    override fun getLayoutId(): Int = R.layout.activity_main2

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


    override fun onDelete(qrData: QRData) {
        viewModel.delete(qrData)
    }

    override fun onItemClick(code: String) {
        code.copyToClipboard(this@MainActivity2)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fbAddNew -> {
                DialogFactory.showAddInfoDialog(supportFragmentManager = supportFragmentManager, this)
            }
        }
    }

    override fun onScanQR() {
        val intent = Intent(this, ScanQRActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    override fun onEnterSetupKey() {
        val intent = Intent(this, ManualActivity::class.java)
        startActivity(intent)
    }

    override fun onInvalidSecretKey() {
        Toast.makeText(this, getString(R.string.app_secret_key_invalid), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveSecretKeySuccess(qrData: QRData) {
        Toast.makeText(this, getString(R.string.save_secret_key_success, "${qrData.appName}: ${qrData.userName}"), Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSecretKeySuccess(qrData: QRData) {
        Toast.makeText(this, getString(R.string.update_secret_key_success, "${qrData.appName}: ${qrData.userName}"), Toast.LENGTH_SHORT).show()
    }

    override fun onGetDataFromDeeplinkSuccess(qrData: QRData) {
        DialogFactory.showConfirmationDialog(
            this@MainActivity2,
            getString(R.string.dialog_add_token_title),
            getString(R.string.dialog_add_token_description, "${qrData.appName}: ${qrData.userName}"),
            getString(R.string.dialog_yes),
            getString(R.string.dialog_no)
        ) { result ->
            if (result) {
                viewModel.save(qrData)
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomeFragment())
                    .commit()
            }

            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, DetailFragment())
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