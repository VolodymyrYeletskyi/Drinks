package com.yeletskyiv.drinks

import android.app.Application
import com.yeletskyiv.drinks.viewmodel.viewModelModule
import com.yeletskyiv.drinks.retrofit.networkModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    val koin = startKoin(this, listOf(networkModule, viewModelModule))
}