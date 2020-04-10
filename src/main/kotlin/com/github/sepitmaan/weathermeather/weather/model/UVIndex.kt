package com.github.sepitmaan.weathermeather.weather.model

enum class UVIndex {
    LOW,
    MODERATE,
    HIGH,
    VERY_HIGH,
    EXTREME;

    companion object {
        fun fromValue(value: Int) = when (value) {
            in 0..2 -> LOW
            in 3..5 -> MODERATE
            in 6..7 -> HIGH
            in 8..10 -> VERY_HIGH
            else -> EXTREME
        }
    }
}