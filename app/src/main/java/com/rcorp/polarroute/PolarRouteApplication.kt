package com.rcorp.polarroute

import android.app.Application
import com.rcorp.polarroute.data.local.AppPreferences
import com.rcorp.polarroute.data.remote.WebClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class PolarRouteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@PolarRouteApplication)
            // use modules
            modules(appModule)
        }
    }

    companion object {
        val appModule = module {

            single { AppPreferences(get()) }

            single {
                WebClient(get())
            }
        }
    }
}