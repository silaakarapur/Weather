package com.example.weather.service

import com.example.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class WeatherAPIService {
    private val BASE_URL="https://api.openweathermap.org/"
    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getData(cityName:String): Single<WeatherModel> {
        return api.getData(cityName)
    }
}