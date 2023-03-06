package com.example.weather.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.example.weather.service.WeatherAPIService
import com.example.weatherapp.model.WeatherModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val weatherAPIService = WeatherAPIService()
    private val disposable = CompositeDisposable()
    val weather = MutableLiveData<WeatherModel>()



val weatherError=MutableLiveData<Boolean>()
    val weatherLoad=MutableLiveData<Boolean>()
    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    fun getDataFromAPI(cityName: String) {

        disposable.add(
            weatherAPIService.getData(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {
                    override fun onSuccess(t: WeatherModel) {
                        weather.value = t
                        weatherError.value=false
                        weatherLoad.value=false
                    }

                    override fun onError(e: Throwable) {

                       weatherError.value=true
                        weatherLoad.value=true
                        println(e)
                    }

                })
        )
    }


}