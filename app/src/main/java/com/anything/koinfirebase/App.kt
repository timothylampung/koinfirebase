package com.anything.koinfirebase

import android.app.Application
import com.anything.koinfirebase.util.Modules
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(Modules.appModules)
        )
    }

}
