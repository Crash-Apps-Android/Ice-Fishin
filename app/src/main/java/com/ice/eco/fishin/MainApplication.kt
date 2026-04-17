package com.ice.eco.fishin
import android.app.Application
import com.ice.eco.fishin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin Dependency Injection
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}