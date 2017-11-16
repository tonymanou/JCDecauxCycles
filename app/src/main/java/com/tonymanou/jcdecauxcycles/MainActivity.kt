package com.tonymanou.jcdecauxcycles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tonymanou.jcdecauxcycles.model.Contract
import com.tonymanou.jcdecauxcycles.view.ContractListFragment
import com.tonymanou.jcdecauxcycles.view.StationDetailsFragment
import com.tonymanou.jcdecauxcycles.view.StationListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_STEP = "main:step"
        private val DEFAULT_STEP = Step.CONTRACT_LIST.name
    }

    private var step = Step.CONTRACT_LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState?.containsKey(EXTRA_STEP) != true) {
            displayContractList()
        } else {
            setStep(Step.valueOf(savedInstanceState.getString(EXTRA_STEP, DEFAULT_STEP)))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_STEP, step.name)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val previousStep = step.previousStep()
        if (previousStep != null) {
            super.onBackPressed()
            setStep(previousStep)
        } else {
            finish()
        }
    }

    private fun displayContractList() = showFragment(
            ContractListFragment.create(),
            Step.CONTRACT_LIST
    )

    fun displayStationList(contract: Contract) = showFragment(
            StationListFragment.create(contract),
            Step.STATION_LIST
    )

    fun displayStationDetails(stationNumber: Int, contractName: String) = showFragment(
            StationDetailsFragment.create(stationNumber, contractName),
            Step.STATION_DETAILS
    )

    private fun showFragment(fragment: Fragment, step: Step) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.root_frame, fragment)
                .addToBackStack(null)
                .commit()

        setStep(step)
    }

    private fun setStep(step: Step) {
        this.step = step
        setTitle(step.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(step.canGoBack)
    }

    private enum class Step(val canGoBack: Boolean, val title: Int) {

        CONTRACT_LIST(false, R.string.contract_list_title),
        STATION_LIST(true, R.string.station_list_title),
        STATION_DETAILS(true, R.string.station_details_title);

        fun previousStep() = when (this) {
            CONTRACT_LIST -> null
            STATION_LIST -> CONTRACT_LIST
            STATION_DETAILS -> STATION_LIST
        }
    }
}
