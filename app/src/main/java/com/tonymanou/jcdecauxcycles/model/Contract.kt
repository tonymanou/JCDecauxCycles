package com.tonymanou.jcdecauxcycles.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Contract(
        @SerializedName("name") val name: String,
        @SerializedName("commercial_name") val commercialName: String,
        @SerializedName("country_code") val countryCode: String,
        @SerializedName("cities") val cities: Array<String>
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArray())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(commercialName)
        parcel.writeString(countryCode)
        parcel.writeStringArray(cities)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contract> {
        override fun createFromParcel(parcel: Parcel): Contract {
            return Contract(parcel)
        }

        override fun newArray(size: Int): Array<Contract?> {
            return arrayOfNulls(size)
        }
    }

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
