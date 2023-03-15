package com.chetssholic.removebackgeround.activity

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.google.android.gms.ads.MobileAds

class RemoveBac : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(
            this
        ) {

            Log.e("TAG", "MobileAds1111112222: " )
        }
        photoApp = this




    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    companion object {
        var photoApp: RemoveBac? = null
            private set
        private val TAG = RemoveBac::class.java.simpleName
    }

}