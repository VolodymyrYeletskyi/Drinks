package com.yeletskyiv.drinks.retrofit

import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createCocktailsApiService(): CocktailsApi {
    val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(CocktailsApi::class.java)
}

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

val networkModule = module {
    single { createCocktailsApiService()}
}