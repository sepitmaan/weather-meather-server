package com.github.sepitmaan.weathermeather.city.repository

import com.github.sepitmaan.weathermeather.city.domain.entity.City
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface CityRepository : ReactiveCrudRepository<City, Long> {
    fun findByNameContainingIgnoreCase(name: String, page: Pageable): Flux<City>
    fun findTop10ByNameContainingIgnoreCase(name: String, page: Pageable): Flux<City>
}