package com.github.sepitmaan.weathermeather.weather.controller

import com.github.sepitmaan.weathermeather.weather.service.WeatherService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("weather")
class WeatherController(val weatherService: WeatherService) {

    @GetMapping("city")
    @Cacheable("weather")
    fun getWeatherByCityId(@RequestParam id: Int, @RequestParam(required = false, defaultValue = "metric") units: String) = weatherService.getWeatherByCityId(id.toString(), units)

    @GetMapping("loc")
    fun getWeatherByLocation(@RequestParam(name = "lat") latitude: Float,
                             @RequestParam(name = "lng") longitude: Float,
                             @RequestParam(required = false, defaultValue = "metric") units: String) = weatherService.getWeatherByLocation(latitude, longitude, units)
}
