package com.github.sepitmaan.weathermeather.city.service

import com.github.sepitmaan.weathermeather.city.domain.entity.City
import com.github.sepitmaan.weathermeather.city.domain.model.CityModel
import com.github.sepitmaan.weathermeather.city.domain.model.Coordination
import com.github.sepitmaan.weathermeather.city.repository.CityRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class CityService(val cityRepository: CityRepository) {

    fun getCitiesByName(name: String, size: Int): Flux<CityModel> {
        val page = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "name"))

        return cityRepository.findTop10ByNameContainingIgnoreCase(name, page)
                .map { convertToModel(it) }
    }

    private fun convertToModel(city: City) = CityModel(
            city.cityId!!,
            city.name,
            city.state,
            city.country,
            Coordination(city.lat, city.lng)
    )
}