package com.anand.demo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


class ConnectionDetector {
    private var connected = false
    val isNetworkAvailable: Boolean
        get() {
            try {
                val connectivityManager =
                    context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                connected = networkInfo != null && networkInfo.isAvailable &&
                        networkInfo.isConnected
                return connected
            } catch (e: Exception) {
                println("CheckConnectivity Exception: " + e.message)
                Log.v("connectivity", e.toString())
            }
            return connected
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        @SuppressLint("StaticFieldLeak")
        private val networkCheck = ConnectionDetector()
        fun getInstance(contx: Context): ConnectionDetector {
            context = contx.applicationContext
            return networkCheck
        }
    }
}