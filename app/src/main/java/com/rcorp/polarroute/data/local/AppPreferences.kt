package com.rcorp.polarroute.data.local;

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AppPreferences constructor(context : Context) {

    private val mPreferences: SharedPreferences =
        context.getSharedPreferences(
            "com.rcorp.PolarRoute", Context.MODE_PRIVATE)

    private val mPrefsEditor: SharedPreferences.Editor = mPreferences.edit()

    /** --------------------------------------------------------------------
    *  -                      USERNAME                              -
    *  --------------------------------------------------------------------*/
    var username: String?
        get() {
            return mPreferences.getString(KEY_USERNAME, null)
        }
        set(username) {
            mPrefsEditor.putString(KEY_USERNAME, username).apply()
        }

    /** --------------------------------------------------------------------
    *  -                      PASSWORD                              -
    *  --------------------------------------------------------------------*/
    var password: String?
        get() {
            return mPreferences.getString(KEY_PASSWORD, null)
        }

        set(password) {
            mPrefsEditor.putString(KEY_PASSWORD, password).apply()
        }

    /** --------------------------------------------------------------------
     *  -                      TOKEN                              -
     *  --------------------------------------------------------------------*/
    var token: String?
        get() {
            return mPreferences.getString(KEY_TOKEN, null)
        }

        set(token) {
            mPrefsEditor.putString(KEY_TOKEN, token).apply()
        }

    /**
     * Clear all local data
     * Firebase token do not change in case of logoff
     * Server url clear is handle in an other place
     */
    fun clear() {
        username = null
        password = null
        token = null
    }

    companion object {
        private const val KEY_USERNAME = "KEY_USERNAME"
        private const val KEY_PASSWORD = "KEY_PASSWORD"
        private const val KEY_TOKEN = "KEY_TOKEN"
    }

}