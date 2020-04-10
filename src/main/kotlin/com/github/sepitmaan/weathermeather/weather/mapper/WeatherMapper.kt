package com.github.sepitmaan.weathermeather.weather.mapper

import com.github.prominence.openweathermap.api.model.response.HourlyForecast
import com.github.prominence.openweathermap.api.model.response.UltravioletIndex
import com.github.prominence.openweathermap.api.model.response.Weather
import com.github.sepitmaan.weathermeather.weather.model.*
import kotlin.math.roundToInt

object WeatherMapper {
    val hourlyForecastSlotCount = 5
    val dailyForecastDaysCount = 5

    fun map(current: Weather, hourly: HourlyForecast, ultravioletIndex: UltravioletIndex): WeatherResponse {
        val city = mapCity(current)
        val uvIndex = UVIndex.fromValue(ultravioletIndex.value.roundToInt())
        val currentStats = mapCurrentStats(current, uvIndex)

        val currentWeather = CurrentWeather(current.dataCalculationTime, city, currentStats)
        val hourlyForecast = mapHourlyForecast(hourly, hourlyForecastSlotCount)
        val dailyForecast = mapDailyForecast(hourly, dailyForecastDaysCount)

        return WeatherResponse(currentWeather, hourlyForecast, dailyForecast)
    }

    private fun mapCity(weather: Weather): City {
        return City(
                weather.cityId
                , weather.cityName
                , weather.country
                , -1
                , weather.weatherSystemInfo.sunriseTimestamp
                , weather.weatherSystemInfo.sunsetTimestamp
                , weather.coordinates.latitude
                , weather.coordinates.longitude
        )
    }

    private fun mapCurrentStats(weather: Weather, uvIndex: UVIndex) = CurrentStats(
            weather.weatherStates[0].conditionId.toInt()
            , weather.weatherStates[0].weatherGroup
            , weather.weatherStates[0].description
            , weather.weatherStates[0].iconId
            , weather.temperature.roundToInt()
            , -1
            , weather.weatherInfo.minimumTemperature.roundToInt()
            , weather.weatherInfo.maximumTemperature.roundToInt()
            , weather.pressure
            , weather.humidityPercentage.toShort()
            , weather.clouds.cloudiness.toShort()
            , weather.wind.speed
            , weather.wind.degrees
            , -1
            , uvIndex
            , weather.rain?.rainVolumeLast3Hrs?.toShort() ?: 0
            , weather.snow?.snowVolumeLast3Hrs?.toShort() ?: 0
    )

    private fun mapHourlyForecast(forecast: HourlyForecast, slotCount: Int) = forecast.forecasts
            .take(slotCount)
            .map {
                HourlyForecast(
                        it.dataCalculationTime
                        , HourlyStats(
                        it.weatherStates[0].conditionId.toInt()
                        , it.weatherStates[0].weatherGroup
                        , it.weatherStates[0].description
                        , it.weatherStates[0].iconId
                        , it.weatherInfo.temperature.roundToInt()
                        , it.weatherInfo.minimumTemperature.roundToInt()
                        , it.weatherInfo.maximumTemperature.roundToInt()
                ))
            }
            .toList()

    private fun mapDailyForecast(forecast: HourlyForecast, days: Int): List<DailyForecast> {
        val step: Int = forecast.cnt / days

        return (0 until forecast.cnt step step)
                .map { index ->
                    val forecast = forecast.forecasts[index]
                    DailyForecast(
                            forecast.dataCalculationTime
                            , DailyStats(
                            forecast.weatherStates[0].conditionId.toInt()
                            , forecast.weatherStates[0].weatherGroup
                            , forecast.weatherStates[0].description
                            , forecast.weatherStates[0].iconId
                            , forecast.weatherInfo.temperature.roundToInt()
                            , forecast.weatherInfo.minimumTemperature.roundToInt()
                            , forecast.weatherInfo.maximumTemperature.roundToInt()
                    ))
                }
                .toList()
    }
}