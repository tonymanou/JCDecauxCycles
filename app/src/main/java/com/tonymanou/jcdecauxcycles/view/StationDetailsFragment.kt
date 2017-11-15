package com.tonymanou.jcdecauxcycles.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tonymanou.jcdecauxcycles.R
import com.tonymanou.jcdecauxcycles.model.Station
import com.tonymanou.jcdecauxcycles.model.Status
import com.tonymanou.jcdecauxcycles.service.CyclesService
import com.tonymanou.jcdecauxcycles.utils.executeAsync
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_station_details.*

class StationDetailsFragment : Fragment() {

    companion object {
        const val EXTRA_STATION_ID = "stationId"
        const val EXTRA_CONTRACT_NAME = "contractName"

        private const val TAG = "StationDetails"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_station_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        refreshData()
    }

    private fun refreshData() {
        val stationId = arguments?.getInt(EXTRA_STATION_ID)
        val contractName = arguments?.getString(EXTRA_CONTRACT_NAME)
        if (stationId == null || contractName == null) {
            displayError("Missing fragment arguments")
            return
        }

        CyclesService.getStationDetails(stationId, contractName)
                .executeAsync(object : Observer<Station> {
                    override fun onSubscribe(d: Disposable) {
                        resetFields(getString(R.string.placeholder))
                    }

                    override fun onNext(station: Station) {
                        refreshFields(station)
                    }

                    override fun onComplete() {
                        // Nothing to do
                    }

                    override fun onError(e: Throwable) {
                        displayError("Unable to load station details", e)
                    }
                })
    }

    private fun resetFields(placeholder: String?) {
        station_name.text = placeholder
        station_number.text = placeholder
        station_address.text = placeholder
        station_position.text = placeholder
        station_contract.text = placeholder
        station_bonus.text = placeholder
        station_status.text = placeholder
        station_bike_stands.text = placeholder
        station_available_bike_stands.text = placeholder
        station_available_bikes.text = placeholder
        station_last_update.text = placeholder
    }

    private fun refreshFields(station: Station) {
        station_name.text = station.name
        station_number.text = station.number.toString()
        station_address.text = station.address
        station_position.text = getString(R.string.station_static_details_position_text,
                station.position.latitude, station.position.longitude)
        station_contract.text = station.contractName
        station_bonus.setText(if (station.bonus) R.string.bonus_yes else R.string.bonus_no)
        station_status.setText(when (station.status) {
            Status.OPEN -> R.string.station_open
            Status.CLOSE -> R.string.station_closed
        })
        station_bike_stands.text = station.bikeStands.toString()
        station_available_bike_stands.text = station.availableBikeStands.toString()
        station_available_bikes.text = station.availableBikes.toString()
        station_last_update.text = station.lastUpdate.toString()
    }

    private fun displayError(message: String, e: Throwable? = null) {
        if (e != null) {
            Log.e(TAG, message, e)
        } else {
            Log.e(TAG, message)
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}
