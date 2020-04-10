package com.github.sepitmaan.weathermeather.city.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table("city")
data class City(
        @Id val cityId: Long? = 0,
        val name: String,
        val state: String,
        val country: String,
        val lat: Float,
        val lng: Float
) : Persistable<Long> {
    override fun isNew() = true
    override fun getId() = cityId
}