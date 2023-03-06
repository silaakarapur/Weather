package com.example.weather.view

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.core.content.SharedPreferencesCompat
import androidx.core.location.LocationCompat
import androidx.core.location.LocationListenerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.Weatheradapter
import com.example.weather.viewmodel.MainViewModel
import com.example.weatherapp.model.WeatherModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.list_clock.*
import kotlinx.android.synthetic.main.list_clock.view.*
import retrofit2.http.GET
import java.lang.Exception
import java.util.*


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    //val weatherAdapter = Weatheradapter(arrayListOf())
    val weather = MutableLiveData<List<WeatherModel>>()


    private var konumYoneticisi: LocationManager? = null
    private var konumDinleyicisi: LocationListener? = null

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        GET =
            ContextWrapper(context).getSharedPreferences(context?.packageName, Context.MODE_PRIVATE)
        SET = GET.edit()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        val cName = GET.getString("cityName", "İstanbul")
        montreal.setText(cName)
        viewModel.refreshData(cName!!)

        getLocation()


        super.onViewCreated(view, savedInstanceState)


    }

    private fun getLiveData() {
        viewModel.weather.observe(viewLifecycleOwner, Observer { data ->

            data.let {
                degree.text = data.main.temp.toString()
            }

        })

        viewModel.weatherLoad.observe(viewLifecycleOwner, Observer { load ->
            load.let {
                if (it) {
                    println("load weather")
                } else {
                    println("dont load weather")
                }

            }

        })

        viewModel.weatherError.observe(viewLifecycleOwner, Observer { error ->
            error.let {
                if (it) {
                    println("hata")
                } else {
                    println("hata yok")
                }
            }

        })
    }

    private fun getLocation() {

        konumYoneticisi = activity?.getSystemService(LOCATION_SERVICE) as LocationManager


        konumDinleyicisi =LocationListener { location ->
            //Lokasyon yada konum değişince yapılacak işlemler

            println("hata yok")
            println(location.latitude)
            println(location.longitude)
            //                try {
            //
            //                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
            //
            //                    var text = ""
            //                    val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            //                    if (list!!.size > 0 && list[0] != null) {
            //                        text = list[0].adminArea.toString()
            //                        println(text)
            //
            //
            //                    }
            //
            //                } catch (e: Exception) {
            //                    e.printStackTrace()
            //                    println("hata")
            //                }
        }


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1

            )
        } else {
            konumDinleyicisi?.let {
                konumYoneticisi?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, it)
                //SET.putString("cityName",text)
                SET.apply()
                //viewModel.refreshData(text)
                getLiveData()

            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty()) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    konumDinleyicisi?.let {
                        konumYoneticisi?.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 1, 1f,
                            it
                        )
                    }
                }
            }


        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}



