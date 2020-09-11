package com.yeletskyiv.drinks.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCocktailsApi(): CocktailsApi = retrofit.create(CocktailsApi::class.java)

    companion object {

        private var instance: Network? = null

        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

        fun getInstance(): Network {
            if (instance == null) instance = Network()
            return instance as Network
        }
    }
}