package com.example.weatherapp.model

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: String,
    val temp_max: String,
    val temp_min: String
)