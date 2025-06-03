package com.suongvong.interviewtest.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity<MainViewModel>(), TokenAdapter.ItemListener, View.OnClickListener, MainNavigator,
    AddKeyBottomSheetDialog.AddKeyBottomSheetDialogListener {

    private var adapter: TokenAdapter? = null
    private var toolBar: Toolbar? = null
    private var rvAuthed: RecyclerView? = null
    private var ctlNoData: ConstraintLayout? = null
    private var fbAddNew: FloatingActionButton? = null
    private var handler:Handler? = Handler(Looper.getMainLooper())

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory((application as AuthenticatorApplication).repository)
    }

    override fun setUpNavigator() = viewModel.setNavigator(this)


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupView() {
        setContentView(R.layout.activity_main)
        toolBar = findViewById(R.id.toolbar)
        rvAuthed = findViewById(R.id.rvAuthed)
        ctlNoData = findViewById(R.id.ctlNoData)
        fbAddNew = findViewById(R.id.fbAddNew)
        fbAddNew?.setOnClickListener(this)

        setSupportActionBar(toolBar)
    }

    override fun setupData() {
        toolBar?.title = getString(R.string.app_name)
        setUpRecyclerView()
        viewModel.allQRData.observe(this) { qrData ->
            qrData?.let {
                if (it.isNotEmpty()) {
                    ctlNoData?.visibility = View.GONE
                } else {
                    ctlNoData?.visibility = View.VISIBLE
                }

                adapter?.updateItems(it)

            }
        }

        onCheckDeeplink()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {

        adapter = TokenAdapter(emptyList(), this)
        rvAuthed?.adapter = adapter
        rvAuthed?.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        rvAuthed?.addItemDecoration(dividerItemDecoration)
    }

    private fun onCheckDeeplink() {

        val data: Uri? = intent?.data
        data?.let {

            val qrUrl = it.query
            Log.d("Deep Link", it.toString())
            if (!qrUrl.isNullOrBlank()) {
                viewModel.onProcessDeeplinkData(it.toString())
            }
        }
    }

    fun getRemainingTime(): Int {
        val currentTimeSeconds = ((System.currentTimeMillis()) / 1000)
        val time = currentTimeSeconds % 30
        return (30 - time).toInt()

    }

    private fun onStartTotpUpdates() {
        val runnable = object : Runnable {
            override fun run() {
                val remainingTime = getRemainingTime()
                adapter?.setTimeRemain(remainingTime)
                adapter?.notifyDataSetChanged()
                handler?.postDelayed(this, 5000)
            }
        }
        handler?.post(runnable)
    }


    override fun onDelete(qrData: QRData) {
        viewModel.delete(qrData)
    }

    override fun onItemClick(code:String) {
        code.copyToClipboard(this@MainActivity)
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
            this@MainActivity,
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

}