package com.suongvong.interviewtest.utils.totp
import com.suongvong.interviewtest.utils.totp.Totp.Companion.INVALID_TOTP_CODE


object OTPUtils {


    private const val SECRET_KEY_MIN_LENGTH = 16
    fun isSecretKeyValid(secretKey: String): Boolean {
        return try {
            if(secretKey.length < SECRET_KEY_MIN_LENGTH){
                return false
            } else {
                val totp = Totp(secretKey)
                val otp = totp.now()
                otp != INVALID_TOTP_CODE.toString()
            }
        } catch (e: Exception) {
            false
        }
    }

    fun generateOTP(secretKey: String): String {
        val totp = Totp(secretKey)
        return totp.now()
    }
}