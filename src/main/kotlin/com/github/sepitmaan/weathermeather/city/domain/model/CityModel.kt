package com.github.sepitmaan.weathermeather.city.domain.model

data class CityModel(
        val id: Long,
        val name: String,
        val state: String,
        val country: String,
        val coord: Coordination
)

data class Coordination(val lat: Float, val lon: Float)