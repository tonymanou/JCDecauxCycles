package com.tonymanou.jcdecauxcycles.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tonymanou.jcdecauxcycles.R
import com.tonymanou.jcdecauxcycles.model.Station

class StationAdapter : RecyclerView.Adapter<StationAdapter.ContractHolder>() {

    private val stationList = ArrayList<Station>()
    private var clickListener: OnStationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_station, parent, false)
        return ContractHolder(view)
    }

    override fun getItemCount() = stationList.size

    override fun onBindViewHolder(
            holder: ContractHolder,
            position: Int
    ) = holder.bind(stationList[position])

    fun setStations(stations: Collection<Station> = emptyList()) {
        stationList.clear()
        if (stations.isNotEmpty()) {
            stationList.addAll(stations)
            stationList.sortBy { it.name.toLowerCase() }
        }
        notifyDataSetChanged()
    }

    fun setOnStationClickListener(listener: OnStationClickListener?) {
        clickListener = listener
    }

    interface OnStationClickListener {

        fun onStationClicked(station: Station)
    }

    inner class ContractHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name : TextView = view.findViewById(R.id.station_name)
        private val address : TextView = view.findViewById(R.id.station_address)

        internal fun bind(station: Station) {
            itemView.setOnClickListener { clickListener?.onStationClicked(station) }
            name.text = station.name
            address.text = station.address
        }
    }
}
