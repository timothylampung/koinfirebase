package com.anything.koinfirebase.util

import com.anything.koinfirebase.view.MainActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object Modules {
    val appModules = module {

        single { FirebaseDatabase.getInstance() }

        single { FirebaseAuth.getInstance() }

        single {
            GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .serializeNulls()
                .setPrettyPrinting()
                .create()
        }

        viewModel {
            MainActivityViewModel(get(), get(), get())
        }


    }
}