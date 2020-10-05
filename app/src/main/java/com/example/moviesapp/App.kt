package com.example.moviesapp

import android.app.Application
import android.content.Context
import com.example.moviesapp.di.AppModule
import toothpick.Scope
import toothpick.Toothpick

class App : Application() {

    companion object {

        lateinit var instance: App

        fun scope(): Scope {
            return instance.appScope
        }
    }

    private lateinit var appScope: Scope

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        instance = this
        openAppScope()
    }

    private fun openAppScope() {
        appScope = Toothpick.openScope(this).apply {
            installModules(AppModule())
        }
    }
}