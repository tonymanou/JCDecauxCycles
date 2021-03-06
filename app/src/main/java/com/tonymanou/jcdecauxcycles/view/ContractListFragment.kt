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
import com.tonymanou.jcdecauxcycles.adapter.ContractAdapter
import com.tonymanou.jcdecauxcycles.model.Contract
import com.tonymanou.jcdecauxcycles.service.CyclesService
import com.tonymanou.jcdecauxcycles.utils.executeAsync
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contract_list.*

class ContractListFragment : Fragment() {

    companion object {
        private const val TAG = "ContractList"

        fun create(): ContractListFragment = ContractListFragment()
    }

    private val adapter = ContractAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_contract_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        contract_list.layoutManager = LinearLayoutManager(activity)
        contract_list.adapter = adapter

        refresh_swipe.setOnRefreshListener { refreshData() }

        adapter.setOnContractClickListener(object : ContractAdapter.OnContractClickListener {
            override fun onContractClicked(contract: Contract) {
                (activity as MainActivity).displayStationList(contract)
            }
        })

        refreshData()
    }

    private fun refreshData() {
        CyclesService.getContracts()
                .executeAsync(object : Observer<Collection<Contract>> {
                    override fun onSubscribe(d: Disposable) {
                        refresh_swipe.isRefreshing = true
                        adapter.setContracts()
                    }

                    override fun onNext(contracts: Collection<Contract>) {
                        adapter.setContracts(contracts)
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

    private fun displayError(message: String, e: Throwable) {
        Log.e(TAG, message, e)
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}