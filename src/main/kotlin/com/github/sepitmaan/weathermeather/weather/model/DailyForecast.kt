package com.github.sepitmaan.weathermeather.weather.model

data class DailyForecast(
        val time: Long,
        val stats: DailyStats
)

data class DailyStats(
        val id: Int,
        val condition: String,
        val description: String,
        val iconId: String,
        val temp: Int,
        val tempMin: Int,
        val tempMax: Int
)