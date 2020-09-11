package com.yeletskyiv.drinks.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("strDrink")
    @Expose val strDrink: String,

    @SerializedName("strDrinkThumb")
    @Expose val strDrinkThumb: String
)