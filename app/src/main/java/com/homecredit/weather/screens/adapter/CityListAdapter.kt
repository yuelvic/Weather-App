package com.homecredit.weather.screens.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homecredit.weather.R
import com.homecredit.weather.data.models.City
import kotlinx.android.synthetic.main.item_city.view.*

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    private val cities: ArrayList<City> = arrayListOf()
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(city: City)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(city: City) {
            view.apply {
                tvTemp.text = "${city.temperature?.temp}\u2103"
                tvCity.text = city.name
                tvDescription.text = city.weather!![0].status
                setBackgroundColor(getColorCoding(city.temperature?.temp!!))
                setOnClickListener { onItemClickListener?.onItemClick(city) }
            }
        }

        private fun getColorCoding(temperature: Double): Int {
            val colorValue = when {
                temperature < 0 -> "#1976D2"
                temperature > 0 && temperature <= 15 -> "#26C6DA"
                temperature > 15 && temperature <= 30 -> "#66BB6A"
                else -> "#26C6DA"
            }
            return Color.parseColor(colorValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(cities[position])

    override fun getItemCount(): Int = cities.size

    fun addCities(cities: ArrayList<City>) {
        this.cities.apply {
            clear()
            addAll(cities)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}