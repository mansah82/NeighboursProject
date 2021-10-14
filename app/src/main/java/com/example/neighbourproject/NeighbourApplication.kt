package com.example.neighbourproject

import android.app.Application
import android.util.Log
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
    companion object{
        private const val TAG = "NeighbourApplication"
        private const val TEST_BUILD = "demo"
    }
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
        Log.i(TAG, "Repository config: ${BuildConfig.BUILD_TYPE}")
        startKoin{
            androidLogger()
            androidContext(this@NeighbourApplication)
            if(BuildConfig.BUILD_TYPE == TEST_BUILD)
                modules((appModuleTest)) //For test purpose
            else
                modules((appModule)) //For a real firebase
        }
    }
}