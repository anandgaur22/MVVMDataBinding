package com.anand.demo.prefrences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PrefrenceManager(var context: Context?) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0

    fun saveResponseDetails(firstname: String?, email: String?, phone: String?, userid: String?) {
        editor = pref.edit()
        editor.putString("name", firstname)
        editor.putString("email", email)
        editor.putString("phone", phone)
        editor.putString("userid", userid)
        editor.commit()
    }


    /*User Details*/


    fun fetchEmail(): String? {
        return pref.getString("email", "")
    }

    fun fetchPhoneNo(): String? {
        return pref.getString("phone", "")
    }

   fun fetchUserId(): String? {
        return pref.getString("userid", "")
    }

    fun validateSession(): Boolean {
        return pref.getBoolean(LOG_IN_OUT, false) == true
    }

    fun saveSessionLogin() {
        editor.putBoolean(LOG_IN_OUT, true)
        editor.commit()
    }

    val isUserLogedOut: Unit
        get() {
            editor = pref.edit()
            editor.putString("name", "")
            editor.putString("email", "")
            editor.putString("phone", "")
            editor.clear()
            editor.commit()
        }

    companion object {
        private const val PREF_NAME = "Demo"
        private const val LOG_IN_OUT = "session"
    }

    init {
        pref = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)!!
        editor = pref.edit()
    }
}