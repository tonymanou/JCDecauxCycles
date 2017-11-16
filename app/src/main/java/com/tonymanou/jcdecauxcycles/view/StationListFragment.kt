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
        private const val BUNDLE_CONTRACT = "stationList:contract"
        private const val TAG = "StationList"

        fun create(contract: Contract): StationListFragment {
            val bundle = Bundle()
            bundle.putParcelable(StationListFragment.BUNDLE_CONTRACT, contract)

            val fragment = StationListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val adapter = StationAdapter()
    private var contract: Contract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contract = arguments?.getParcelable(BUNDLE_CONTRACT)
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

        refresh_swipe.setOnRefreshListener { refreshData() }

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
                        refresh_swipe.isRefreshing = true
                        adapter.setStations()
                    }

                    override fun onNext(stations: Collection<Station>) {
                        adapter.setStations(stations)
                    }

                    override fun onComplete() {
                        refresh_swipe.isRefreshing = false
                    }

                    override fun onError(e: Throwable) {
                        refresh_swipe.isRefreshing = false
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
