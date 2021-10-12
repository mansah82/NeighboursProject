package com.example.neighbourproject

import android.app.Application
import com.example.neighbourproject.location.LocationRepository
import com.example.neighbourproject.location.LocationService
import com.example.neighbourproject.neighbour.NeighboursRepository
import com.example.neighbourproject.neighbour.NeighboursRepositoryTest
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.user.UserRepository
import com.example.neighbourproject.user.UserRepositoryTest
import com.example.neighbourproject.user.UserService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import org.koin.dsl.module

class NeighbourApplication: Application() {
    private val appModule = module {
        single<NeighboursService> { NeighboursRepository() }
        single<LocationService> { LocationRepository() }
        single<UserService> { UserRepository()}
    }

    private val appModuleTest = module {
        single<NeighboursService> { NeighboursRepositoryTest() }
        single<LocationService> { LocationRepository() }
        single<UserService> { UserRepositoryTest() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@NeighbourApplication)
            //modules((appModule)) //For a real firebase
            modules((appModuleTest)) //For test purpose
        }
    }
}