package com.example.moviesapp

import android.app.Application
import android.content.Context
import toothpick.Scope
import toothpick.Toothpick

class App : Application() {

    companion object {

        lateinit var instance: App
        val scope = instance.appScope
    }

    private lateinit var appScope: Scope

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        openAppScope()
    }

    private fun openAppScope() {
        appScope = Toothpick.openScope(this)
    }
}