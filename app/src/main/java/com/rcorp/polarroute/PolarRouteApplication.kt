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
            androidLogger()
            androidContext(this@PolarRouteApplication)
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