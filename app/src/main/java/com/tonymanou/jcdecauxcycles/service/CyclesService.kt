package com.tonymanou.jcdecauxcycles.service

import com.tonymanou.jcdecauxcycles.BuildConfig.CYCLES_API_KEY
import com.tonymanou.jcdecauxcycles.BuildConfig.CYCLES_ENDPOINT
import com.tonymanou.jcdecauxcycles.net.CyclesApi
import com.tonymanou.jcdecauxcycles.utils.asObservable

object CyclesService {

    private val cyclesApi = CyclesApi.create(CYCLES_ENDPOINT, CYCLES_API_KEY)

    fun getStations() = cyclesApi.getStations().asObservable()

    fun getContracts() = cyclesApi.getContracts().asObservable()

    fun getStationsForContract(
            contractName: String
    ) = cyclesApi.getStationsForContract(contractName).asObservable()

    fun getStationDetails(
            stationNumber: Int,
            contractName: String
    ) = cyclesApi.getStationDetails(stationNumber, contractName).asObservable()
}
