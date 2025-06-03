package com.suongvong.interviewtest.utils.totp


import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.nio.ByteBuffer
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone


class Totp(private val secret: String) {

    companion object{
        private const val DIGIT_SIX = 1000000
        private const val SHA1="HMACSHA1"
        private const val ALGORITHM = "RAW"
        private const val INTERVAL = 30

        private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
        private val DIGITS = ALPHABET.toCharArray()
        private val MASK = DIGITS.size - 1
        private val SHIFT = Integer.numberOfTrailingZeros(DIGITS.size)
        private val CHAR_MAP = HashMap<Char, Int>()
        private const val SEPARATOR = "-"
        const val INVALID_TOTP_CODE = -1

    }

    init {
        for (i in DIGITS.indices) {
            CHAR_MAP[DIGITS[i]] = i
        }
    }

    fun now(): String {
        return leftPadding(hash(secret, getCurrentInterval()))
    }

    private fun hash(secret: String, interval: Long): Int {
        var hash = byteArrayOf()
        try {
            hash = digest(SHA1, decode(secret), interval)
            bytesToInt(hash)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bytesToInt(hash)
    }

    private fun bytesToInt(hash: ByteArray): Int {
        if (hash.isEmpty()) return INVALID_TOTP_CODE
        val offset = hash[hash.size - 1].toInt() and 0xf

        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        return binary % DIGIT_SIX
    }

    private fun leftPadding(otp: Int): String {
        if (otp == INVALID_TOTP_CODE) {
            return otp.toString()
        }
        return "%06d".format(otp)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun digest(hash: String, secret: ByteArray, currentInterval: Long): ByteArray {
        val challenge = ByteBuffer.allocate(8).putLong(currentInterval).array()
        val mac = Mac.getInstance(hash)
        val macKey = SecretKeySpec(secret, ALGORITHM)
        mac.init(macKey)
        return mac.doFinal(challenge)
    }

    private fun getCurrentInterval(): Long {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        val currentTimeSeconds = calendar.timeInMillis / 1000
        return currentTimeSeconds / INTERVAL
    }


    @Throws(DecodingException::class)
    fun decode(encoded: String): ByteArray {
        var encodedStr = encoded.trim()
        encodedStr = encodedStr.replaceFirst("[=]*$".toRegex(), "").uppercase(Locale.US)
        if (encodedStr.isEmpty()) {
            return byteArrayOf()
        }
        val encodedLength = encodedStr.length
        val outLength = encodedLength * SHIFT / 8
        val result = ByteArray(outLength)
        var buffer = 0
        var next = 0
        var bitsLeft = 0
        for (c in encodedStr) {
            val value = CHAR_MAP[c] ?: throw DecodingException("Illegal character: $c")
            buffer = (buffer shl SHIFT) or (value and MASK)
            bitsLeft += SHIFT
            if (bitsLeft >= 8) {
                result[next++] = (buffer shr (bitsLeft - 8)).toByte()
                bitsLeft -= 8
            }
        }
        return result
    }

    class DecodingException(message: String) : Exception(message)

}
