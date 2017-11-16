package com.tonymanou.jcdecauxcycles.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tonymanou.jcdecauxcycles.MainActivity
import com.tonymanou.jcdecauxcycles.R
import com.tonymanou.jcdecauxcycles.adapter.StationAdapter
import com.tonymanou.jcdecauxcycles.model.Contract
import com.tonymanou.jcdecauxcycles.model.Station
import com.tonymanou.jcdecauxcycles.service.CyclesService
import com.tonymanou.jcdecauxcycles.utils.executeAsync
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_station_list.*
import kotlinx.android.synthetic.main.list_item_contract.*

class StationListFragment : Fragment() {

    companion object {
        const val EXTRA_CONTRACT = "contract"

        private const val TAG = "StationList"
    }

    private val adapter = StationAdapter()
    private var contract: Contract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contract = arguments?.getParcelable(EXTRA_CONTRACT)
        if (contract == null) {
            displayError("Missing fragment argument")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_station_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        station_list.layoutManager = LinearLayoutManager(activity)
        station_list.adapter = adapter

        adapter.setOnStationClickListener(object : StationAdapter.OnStationClickListener {
            override fun onStationClicked(station: Station) {
                (activity as MainActivity).displayStationDetails(station.number,
                        station.contractName)
            }
        })

        displayContract()
        refreshData()
    }

    private fun displayContract() {
        val contract = contract ?: return
        contract_name.text = contract.commercialName
        contract_country.text = contract.countryCode
        contract_cities.text = contract.cities.joinToString(", ")
    }

    private fun refreshData() {
        val contract = contract ?: return

        CyclesService.getStationsForContract(contract.name)
                .executeAsync(object : Observer<Collection<Station>> {
                    override fun onSubscribe(d: Disposable) {
                        adapter.setStations()
                    }

                    override fun onNext(stations: Collection<Station>) {
                        adapter.setStations(stations)
                    }

                    override fun onComplete() {
                        // Nothing to do
                    }

                    override fun onError(e: Throwable) {
                        displayError("Unable to load station list", e)
                    }
                })
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
