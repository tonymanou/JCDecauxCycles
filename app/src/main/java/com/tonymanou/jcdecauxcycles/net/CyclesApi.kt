package com.tonymanou.jcdecauxcycles.net

import com.tonymanou.jcdecauxcycles.model.Contract
import com.tonymanou.jcdecauxcycles.model.Station
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CyclesApi {

    @GET("vls/v1/stations")
    fun getStations(): Call<Collection<Station>>

    @GET("vls/v1/contracts")
    fun getContracts(): Call<Collection<Contract>>

    @GET("vls/v1/stations")
    fun getStationsForContract(@Query("contract") contractName: String): Call<Collection<Station>>

    @GET("vls/v1/stations/{station_number}")
    fun getStationDetails(
            @Path("station_number") stationNumber: Int,
            @Query("contract") contractName: String
    ): Call<Station>

    companion object Factory {
        fun create(baseUrl: String, apiKey: String): CyclesApi {
            val client = OkHttpClient.Builder()
                    .addInterceptor(AuthenticationInterceptor(apiKey))
                    .build()
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(CyclesApi::class.java)
        }
    }
}
