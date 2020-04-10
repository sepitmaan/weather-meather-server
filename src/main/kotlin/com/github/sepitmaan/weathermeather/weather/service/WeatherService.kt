package com.github.sepitmaan.weathermeather.weather.service

import com.github.prominence.openweathermap.api.OpenWeatherMapManager
import com.github.sepitmaan.weathermeather.weather.client.OpenWeatherClient
import com.github.sepitmaan.weathermeather.weather.mapper.WeatherMapper
import com.github.sepitmaan.weathermeather.weather.model.WeatherResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class WeatherService(@Value("\${openweather.appId}") val appId: String) {
    lateinit var client: OpenWeatherClient

    @PostConstruct
    fun init() {
        client = OpenWeatherClient(OpenWeatherMapManager(appId))
    }

    fun getWeatherByCityId(cityId: String, units: String): Mono<WeatherResponse> {
        val weather = client.getCurrentWeatherByCityId(cityId, units)
        val hourlyForecast = client.getHourlyForecastByCityId(cityId, units)
        val uvIndex = weather.flatMap { client.getUVIndex(it.coordinates) }

        return Mono.zip(weather, hourlyForecast, uvIndex)
                .map { WeatherMapper.map(it.t1, it.t2, it.t3) }
    }

    fun getWeatherByLocation(latitude: Float, longitude: Float, units: String): Mono<WeatherResponse> {
        val weather = client.getCurrentWeatherByLocation(latitude, longitude, units)
        val hourlyForecast = client.getHourlyForecastByLocation(latitude, longitude, units)
        val uvIndex = weather.flatMap { client.getUVIndex(it.coordinates) }

        return Mono.zip(weather, hourlyForecast, uvIndex)
                .map { WeatherMapper.map(it.t1, it.t2, it.t3) }
    }
}