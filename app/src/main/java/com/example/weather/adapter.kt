package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Weatheradapter.ItemListViewHolder
import com.example.weather.databinding.ListClockBinding
import com.example.weatherapp.model.WeatherModel
import kotlinx.android.synthetic.main.list_clock.view.*

class Weatheradapter(val itemList: ArrayList<WeatherModel>) :
    RecyclerView.Adapter<ItemListViewHolder>() {
        class ItemListViewHolder(val view: View):RecyclerView.ViewHolder(view){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.list_clock,parent,false)
        return ItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
     holder.view.degree.text=itemList[position].coord.toString()
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    fun updateWeatherList(newCountryList: List<WeatherModel>) {
        itemList.clear()
        itemList.addAll(newCountryList)
        notifyDataSetChanged()
    }


}