package com.ice.eco.fishin.di

import androidx.room.Room
import com.ice.eco.fishin.data.database.EcoDatabase
import com.ice.eco.fishin.data.database.HealthyFoodProvider
import com.ice.eco.fishin.data.store.VisitingStreakProvider
import com.ice.eco.fishin.data.store.dataStore
import com.ice.eco.fishin.ui.MainViewModel
import com.ice.eco.fishin.ui.checker.CheckerViewModel
import com.ice.eco.fishin.ui.dashboard.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Room Setup
    single {
        Room.databaseBuilder(androidContext(), EcoDatabase::class.java, "ecotrack_db")
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single {
        HealthyFoodProvider(androidContext())
    }
    single { get<EcoDatabase>().ecoDao() }

    // DataStore Setup
    single { androidContext().dataStore }
    single { VisitingStreakProvider(get()) }

    // ViewModels
    viewModelOf(::MainViewModel)
    viewModelOf(::CheckerViewModel)
    viewModelOf(::HomeViewModel)
}
