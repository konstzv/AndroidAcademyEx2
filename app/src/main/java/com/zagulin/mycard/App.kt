package com.zagulin.mycard

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins
import net.danlew.android.joda.JodaTimeAndroid
import toothpick.Toothpick
import toothpick.config.Module


class App : Application() {

    companion object {
        const val SHARED_PREFS_NAME = "NY_TIMES_PREFS"

        enum class Scopes(name: String) {
            APP_SCOPE("AppScope"),
            FEED_SCOPE("FeedScope")
        }
    }


    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        RxJavaPlugins.setErrorHandler { e ->
            Log.w("RX", "Undeliverable exception received, not sure what to do", e)
        }
        val appScope = Toothpick.openScope(Scopes.APP_SCOPE.name)
        appScope.installModules(object : Module() {
            init {
                bind(Context::class.java).toInstance(applicationContext)
                bind(SharedPreferences::class.java)
                        .toInstance(getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE))
            }
        })

    }


}