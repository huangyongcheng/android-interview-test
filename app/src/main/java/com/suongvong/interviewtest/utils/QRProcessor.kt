package com.suongvong.interviewtest.utils

import com.suongvong.interviewtest.constants.FORMAT_UTF_8
import com.suongvong.interviewtest.constants.ISSUER
import com.suongvong.interviewtest.constants.OTP_AUTH_TOTP
import com.suongvong.interviewtest.constants.SECRET
import com.suongvong.interviewtest.model.QRData
import com.suongvong.interviewtest.utils.totp.OTPUtils
import java.net.URLDecoder

object QRProcessor {
    fun processScannedData(
        qrUrl: String,
        onSuccess: (QRData) -> Unit,
        onError: () -> Unit
    ) {
        try {
            if (!qrUrl.startsWith(OTP_AUTH_TOTP)) {
                onError()
                return
            }

            val decodedUrl = URLDecoder.decode(qrUrl, FORMAT_UTF_8)
            val accountInfo = decodedUrl.substringAfter(OTP_AUTH_TOTP).substringBefore("?")
            val issuer = decodedUrl.substringAfter(ISSUER, "").substringBefore("&")
            val secretKey = decodedUrl.substringAfter(SECRET, "").substringBefore("&")
            val userName = accountInfo.substringAfter(":")

            if (OTPUtils.isSecretKeyValid(secretKey)) {
                val qrData = QRData(secretKey = secretKey, appName = issuer, userName = userName)
                onSuccess(qrData)
            } else {
                onError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onError()
        }
    }
}