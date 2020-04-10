package com.github.sepitmaan.weathermeather.weather.model

data class WeatherResponse(
        val current: CurrentWeather,
        val hourly: List<HourlyForecast>,
        val daily: List<DailyForecast>
)