package com.tonymanou.jcdecauxcycles.model

import com.google.gson.annotations.SerializedName

data class Station(
        @SerializedName("number") val number: Int,
        @SerializedName("contract_name") val contractName: String,
        @SerializedName("name") val name: String,
        @SerializedName("address") val address: String,
        @SerializedName("position") val position: Position,
        @SerializedName("banking") val banking: Boolean,
        @SerializedName("bonus") val bonus: Boolean,
        @SerializedName("status") val status: Status,
        @SerializedName("bike_stands") val bikeStands: Int,
        @SerializedName("available_bike_stands") val availableBikeStands: Int,
        @SerializedName("available_bikes") val availableBikes: Int,
        @SerializedName("last_update") val lastUpdate: Long
)
