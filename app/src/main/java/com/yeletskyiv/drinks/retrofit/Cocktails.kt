package com.yeletskyiv.drinks.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cocktails(
    @SerializedName("drinks")
    @Expose val drinks: List<Cocktail>
)