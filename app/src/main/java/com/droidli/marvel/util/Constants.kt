package com.droidli.marvel.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val API_KEY = "83d7735e0ba94d74ad6273e0559cd61a"
        const val PRIVATE_KEY = "6d97327a915d8325ddc1e7fcc38403b37b043c49"
        const val limit = "20"
        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}