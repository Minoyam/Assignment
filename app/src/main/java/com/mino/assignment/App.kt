package com.mino.assignment

import android.app.Application
import android.content.Context
import com.mino.assignment.di.networkModule
import com.mino.assignment.di.repositoryModule
import com.mino.assignment.di.sourceModule
import com.mino.assignment.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        setUpKoin()
    }

    fun context(): Context = applicationContext

    private fun setUpKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    sourceModule,
                    viewModelModule
                )
            )
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}