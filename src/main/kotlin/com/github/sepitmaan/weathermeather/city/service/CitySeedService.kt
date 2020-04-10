package com.github.sepitmaan.weathermeather.city.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.sepitmaan.weathermeather.city.domain.entity.City
import com.github.sepitmaan.weathermeather.city.domain.model.CityModel
import com.github.sepitmaan.weathermeather.city.repository.CityRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.io.File
import javax.annotation.PostConstruct

@Service
class CitySeedService(dbClient: DatabaseClient, val cityRepository: CityRepository) {
    init {
        dbClient.execute("""
                    DROP TABLE IF EXISTS city;
                    CREATE TABLE IF NOT EXISTS city
                    (
                        city_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        state VARCHAR(255),
                        country VARCHAR(255) NOT NULL,
                        lat DECIMAL(11, 7),
                        lng DECIMAL(11, 7)
                    );
                """.trimIndent())
                .fetch()
                .rowsUpdated()
                .subscribe()
    }

    @PostConstruct
    fun seedDatabase() {
        println("inserting cities to database.")

        val reader = File("/app/city.json").bufferedReader()

        val citiesFlux = jacksonObjectMapper().readValue<List<CityModel>>(reader)
                .toFlux()
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap { convertToCity(it) }

        cityRepository.saveAll(citiesFlux)
                .doOnComplete { println("insert finished.") }
                .subscribe()
    }

    private fun convertToCity(model: CityModel) = City(
            model.id,
            model.name,
            model.state,
            model.country,
            model.coord.lat,
            model.coord.lon
    ).toMono()
}