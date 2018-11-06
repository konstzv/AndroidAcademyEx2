package com.zagulin.mycard

import android.app.Application
import android.util.Log
import net.danlew.android.joda.JodaTimeAndroid

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        RxJavaPlugins.setErrorHandler { e ->

            Log.w("RX","Undeliverable exception received, not sure what to do", e)
        }
    }

}