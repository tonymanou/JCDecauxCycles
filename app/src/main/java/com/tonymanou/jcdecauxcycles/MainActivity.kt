package com.tonymanou.jcdecauxcycles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.tonymanou.jcdecauxcycles.view.ContractListFragment
import com.tonymanou.jcdecauxcycles.view.StationDetailsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun displayContractList() {
        val fragment = ContractListFragment()
        setTitle(R.string.contract_list_title)
        showFragment(fragment)
    }

    private fun displayStationDetails(stationNumber: Int, contractName: String) {
        val bundle = Bundle()
        bundle.putInt(StationDetailsFragment.EXTRA_STATION_ID, stationNumber)
        bundle.putString(StationDetailsFragment.EXTRA_CONTRACT_NAME, contractName)

        val fragment = StationDetailsFragment()
        fragment.arguments = bundle
        setTitle(R.string.station_details_title)
        showFragment(fragment)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_frame, fragment)
                .commit()
    }
}
