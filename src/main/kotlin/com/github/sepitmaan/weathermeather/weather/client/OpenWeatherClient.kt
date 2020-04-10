package com.github.sepitmaan.weathermeather.weather.client

import com.github.prominence.openweathermap.api.OpenWeatherMapManager
import com.github.prominence.openweathermap.api.model.Coordinates
import com.github.prominence.openweathermap.api.model.response.HourlyForecast
import com.github.prominence.openweathermap.api.model.response.Weather
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class OpenWeatherClient(private val client: OpenWeatherMapManager) {

    fun getCurrentWeatherByCityId(cityId: String, units: String): Mono<Weather> = client.weatherRequester
            .setUnitSystem(units)
            .getByCityId(cityId)
            .toMono()

    fun getCurrentWeatherByLocation(latitude: Float, longitude: Float, units: String): Mono<Weather> = client.weatherRequester
            .setUnitSystem(units)
            .getByCoordinates(latitude.toDouble(), longitude.toDouble())
            .toMono()

    fun getHourlyForecastByCityId(cityId: String, units: String): Mono<HourlyForecast> = client.hourlyForecastRequester
            .setUnitSystem(units)
            .getByCityId(cityId)
            .toMono()

    fun getHourlyForecastByLocation(latitude: Float, longitude: Float, units: String): Mono<HourlyForecast> = client.hourlyForecastRequester
            .setUnitSystem(units)
            .getByCoordinates(latitude.toDouble(), longitude.toDouble())
            .toMono()

    fun getUVIndex(coordinates: Coordinates) = client.getUltravioletIndexRequester(coordinates)
            .currentUVIndex
            .toMono()
}