package com.tonymanou.jcdecauxcycles.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tonymanou.jcdecauxcycles.R
import com.tonymanou.jcdecauxcycles.model.Contract
import kotlinx.android.synthetic.main.list_item_contract.view.*

class ContractAdapter : RecyclerView.Adapter<ContractAdapter.ContractHolder>() {

    private val contractList = ArrayList<Contract>()
    private var clickListener: OnContractClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_contract, parent, false)
        return ContractHolder(view)
    }

    override fun getItemCount() = contractList.size

    override fun onBindViewHolder(
            holder: ContractHolder,
            position: Int
    ) = holder.bind(contractList[position])

    fun setContracts(contracts: Collection<Contract> = emptyList()) {
        contractList.clear()
        if (contracts.isNotEmpty()) {
            contractList.addAll(contracts)
            contractList.sortBy { it.countryCode + it.commercialName.toLowerCase() }
        }
        notifyDataSetChanged()
    }

    fun setOnContractClickListener(listener: OnContractClickListener?) {
        clickListener = listener
    }

    interface OnContractClickListener {

        fun onContractClicked(contract: Contract)
    }

    inner class ContractHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.contract_name
        private val country = view.contract_country
        private val cities = view.contract_cities

        internal fun bind(contract: Contract) {
            itemView.setOnClickListener { clickListener?.onContractClicked(contract) }
            name.text = contract.commercialName
            country.text = contract.countryCode
            cities.text = contract.cities.joinToString(", ")
        }
    }
}
