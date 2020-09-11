package com.yeletskyiv.drinks

import android.app.Application
import com.yeletskyiv.drinks.ui.NetworkViewModel

class App : Application() {

    private val viewModel = NetworkViewModel.getInstance()

    fun getViewModel(): NetworkViewModel = viewModel

    companion object {

        private var instance: App? = null

        fun getInstance() : App {
            return if(instance == null) App()
            else instance as App
        }
    }
}