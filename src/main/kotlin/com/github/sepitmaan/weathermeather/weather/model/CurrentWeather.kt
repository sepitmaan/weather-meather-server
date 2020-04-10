package com.github.sepitmaan.weathermeather.weather.model

data class CurrentWeather(
        val time: Long,
        val city: City,
        val stats: CurrentStats
)

data class City(
        val id: Long,
        val name: String,
        val country: String,
        val timezone: Int,
        val sunrise: Long,
        val sunset: Long,
        val lat: Float,
        val lng: Float
)

data class CurrentStats(
        val id: Int,
        val condition: String,
        val description: String,
        val iconId: String,
        val temp: Int,
        val feelsLike: Int,
        val tempMin: Int,
        val tempMax: Int,
        val pressure: Short,
        val humidity: Short,
        val cloudiness: Short,
        val windSpeed: Float,
        val windDegree: Short,
        val visibility: Int,
        val uv: UVIndex,
        val rain3h: Short,
        val snow3h: Short
)