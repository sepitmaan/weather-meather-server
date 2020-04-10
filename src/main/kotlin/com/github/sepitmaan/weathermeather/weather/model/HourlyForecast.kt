package com.github.sepitmaan.weathermeather.weather.model

data class HourlyForecast(
        val time: Long,
        val stats: HourlyStats
)

data class HourlyStats(
        val id: Int,
        val condition: String,
        val description: String,
        val iconId: String,
        val temp: Int,
        val tempMin: Int,
        val tempMax: Int
)