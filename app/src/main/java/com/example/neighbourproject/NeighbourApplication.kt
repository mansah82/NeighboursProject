package com.example.neighbourproject

import android.app.Application
import android.util.Log
import com.example.neighbourproject.neighbour.NeighboursRepository
import com.example.neighbourproject.neighbour.NeighboursService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NeighbourApplication: Application() {
    private val appModule = module {
        single<NeighboursService> { NeighboursRepository() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@NeighbourApplication)
            modules((appModule))
        }
    }
}