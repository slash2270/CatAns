package com.example.catans.model

import com.example.catans.util.Utils
import com.google.gson.annotations.SerializedName

/**
{
    "data": {
    "CAD": 1.3523701657,
    "EUR": 0.9250601007,
    "USD": 1
}
**/

data class Currency(
    @SerializedName(Utils.data) val data: Data?,
)

data class Data(
    @SerializedName(Utils.CAD) val cad: Double = 0.0,
    @SerializedName(Utils.EUR) val eur: Double = 0.0,
    @SerializedName(Utils.USD) val usd: Double = 0.0
)