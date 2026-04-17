package com.ecohabit.compass
import android.app.Application
import com.ecohabit.compass.di.appModule
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