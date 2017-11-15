package com.tonymanou.jcdecauxcycles.model

import com.google.gson.annotations.SerializedName

data class Position(
        @SerializedName("lat") val latitude: Double,
        @SerializedName("lng") val longitude: Double
)
