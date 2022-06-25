package com.honito.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto (
    val name: String,
    val price: Double
)