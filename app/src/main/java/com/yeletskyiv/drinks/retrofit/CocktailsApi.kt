package com.yeletskyiv.drinks.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {

    @GET("filter.php")
    suspend fun getDrinks(@Query("c") type: String): Response<Cocktails>

    @GET("list.php?c=list")
    suspend fun getCategories(): Response<Categories>
}