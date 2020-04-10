package com.github.sepitmaan.weathermeather.city.controller

import com.github.sepitmaan.weathermeather.city.service.CityService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Max

@RestController
@RequestMapping("city")
class CityController(val cityService: CityService) {

    @GetMapping
    fun getCities(@RequestParam(required = true) name: String
                  , @RequestParam(required = false, defaultValue = "10") @Max(20) size: Int) = cityService.getCitiesByName(name, size)
}