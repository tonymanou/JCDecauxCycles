package com.tonymanou.jcdecauxcycles.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Contract(
        @SerializedName("name") val name: String,
        @SerializedName("commercial_name") val commercialName: String,
        @SerializedName("country_code") val countryCode: String,
        @SerializedName("cities") val cities: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contract

        if (name != other.name) return false
        if (commercialName != other.commercialName) return false
        if (countryCode != other.countryCode) return false
        if (!Arrays.equals(cities, other.cities)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + commercialName.hashCode()
        result = 31 * result + countryCode.hashCode()
        result = 31 * result + Arrays.hashCode(cities)
        return result
    }
}
