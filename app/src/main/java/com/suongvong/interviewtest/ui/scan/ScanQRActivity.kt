package com.suongvong.interviewtest.ui.scan

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.AuthenticatorApplication
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.constants.REQUEST_CODE_CAMERA_PERMISSIONS
import com.suongvong.interviewtest.constants.REQUIRED_CAMERA_PERMISSIONS
import com.suongvong.interviewtest.dialog.DialogFactory
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.ui.base.BaseActivity
import com.suongvong.interviewtest.utils.PermissionUtils
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

class ScanQRActivity : BaseActivity<ScanQRViewModel>(), ScanQRNavigator {

    private lateinit var previewView: PreviewView
    private var cameraProvider: ProcessCameraProvider? = null
    private var hasDataResult: Boolean = false

    companion object {
        private const val TIME_DELAY_SCAN = 1000L
    }
    override fun getViewModelClass() = ScanQRViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return ScanQRViewModelFactory((application as AuthenticatorApplication).repository)
    }

    override fun setUpNavigator() {
        viewModel.setNavigator(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_scan_qr_code

    override fun setupView() {
        previewView = findViewById(R.id.previewView)
    }

    override fun setupData() {
        if (PermissionUtils.hasPermissions(this@ScanQRActivity, arrayOf(REQUIRED_CAMERA_PERMISSIONS))) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(REQUIRED_CAMERA_PERMISSIONS), REQUEST_CODE_CAMERA_PERMISSIONS)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                processImageProxy(imageProxy)
            }

            cameraProvider?.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(this))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty() && !hasDataResult) {
                        hasDataResult = true
                        barcodes[0].rawValue?.let { viewModel.onProcessScannedData(it) }
                    }
                }
                .addOnFailureListener {
                    Log.e("QR Code", "Scanning failed", it)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSIONS) {
            if (PermissionUtils.hasPermissions(this@ScanQRActivity, arrayOf(REQUIRED_CAMERA_PERMISSIONS))) {
                startCamera()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onInvalidSecretKey() {
        DialogFactory.showInfoDialog(
            this@ScanQRActivity,
            getString(R.string.dialog_invalid_title),
            getString(R.string.app_qr_code_invalid),
            getString(R.string.dialog_ok)
        ) {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                hasDataResult = false
            }, TIME_DELAY_SCAN)

        }
    }

    override fun onSaveSecretKeySuccess(qrData: QRData) {
        Toast.makeText(this, getString(R.string.save_secret_key_success, "${qrData.appName}: ${qrData.userName}"), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onUpdateSecretKeySuccess(qrData: QRData) {
        Toast.makeText(this, getString(R.string.update_secret_key_success, "${qrData.appName}: ${qrData.userName}"), Toast.LENGTH_SHORT).show()
        finish()
    }
}