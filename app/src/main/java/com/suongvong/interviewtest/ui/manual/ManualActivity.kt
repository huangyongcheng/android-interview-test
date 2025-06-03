package com.suongvong.interviewtest.ui.manual

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.AuthenticatorApplication
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.ui.base.BaseActivity
import com.suongvong.interviewtest.utils.KeyboardUtils
import com.suongvong.interviewtest.utils.totp.OTPUtils

class ManualActivity : BaseActivity<ManualViewModel>(), View.OnClickListener {

    private var etAppName: AppCompatEditText? = null
    private var etUserName: AppCompatEditText? = null
    private var etSecretKey: AppCompatEditText? = null
    private var btnAdd: Button? = null
    private var toolBar: Toolbar? = null

    override fun getViewModelClass() = ManualViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return ManualViewModelFactory((application as AuthenticatorApplication).repository)
    }

    override fun setUpNavigator() {

    }

    override fun getLayoutId(): Int = R.layout.manual_activity

    override fun setupView() {
        etAppName = findViewById(R.id.etAppName)
        etUserName = findViewById(R.id.etUserName)
        etSecretKey = findViewById(R.id.etSecretKey)
        btnAdd = findViewById(R.id.btnAdd)
        toolBar = findViewById(R.id.toolbar)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        btnAdd?.setOnClickListener(this)
    }

    override fun setupData() {
        KeyboardUtils.showKeyboard(etAppName)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.btnAdd) {

            val secretKey = etSecretKey?.text.toString().trim()
            val appName = etAppName?.text.toString().trim()
            val userName = etUserName?.text.toString().trim()
            if (secretKey.isNotBlank() && appName.isNotBlank()) {
                if (OTPUtils.isSecretKeyValid(secretKey)) {
                    val qrData = QRData(
                        secretKey = secretKey,
                        appName = appName,
                        userName = userName,
                    )

                    viewModel.insert(qrData)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.app_secret_key_invalid), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}