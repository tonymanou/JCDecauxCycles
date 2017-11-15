package com.tonymanou.jcdecauxcycles

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.tonymanou.jcdecauxcycles.model.Contract
import com.tonymanou.jcdecauxcycles.view.ContractListFragment
import com.tonymanou.jcdecauxcycles.view.StationDetailsFragment
import com.tonymanou.jcdecauxcycles.view.StationListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ALREADY_LAUNCHED = "alreadyLaunched"
        const val EXTRA_STATE = "state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState?.containsKey(EXTRA_ALREADY_LAUNCHED) != true) {
            displayContractList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(EXTRA_ALREADY_LAUNCHED, true)
    }

    private fun displayContractList() {
        val fragment = ContractListFragment()
        setTitle(R.string.contract_list_title)
        showFragment(fragment)
    }

    fun displayStationList(contract: Contract) {
        val bundle = Bundle()
        bundle.putParcelable(StationListFragment.EXTRA_CONTRACT, contract)

        val fragment = StationListFragment()
        fragment.arguments = bundle
        setTitle(R.string.station_list_title)
        showFragment(fragment)
    }

    fun displayStationDetails(stationNumber: Int, contractName: String) {
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
                .addToBackStack(null)
                .commit()
    }
}
