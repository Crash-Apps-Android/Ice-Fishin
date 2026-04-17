package com.ecohabit.compass.di

import androidx.room.Room
import com.ecohabit.compass.data.store.dataStore
import com.ecohabit.compass.ui.MainViewModel
import com.ecohabit.compass.ui.checker.CheckerViewModel
import com.ecohabit.compass.ui.dashboard.HomeViewModel
import com.ecohabit.compass.data.database.EcoDatabase
import com.ecohabit.compass.data.database.HealthyFoodProvider
import com.ecohabit.compass.data.store.VisitingStreakProvider
import com.ecohabit.compass.data.store.dataStore
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
