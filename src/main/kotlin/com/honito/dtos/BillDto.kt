package com.honito.dtos

import com.honito.models.Item
import kotlinx.serialization.Serializable

@Serializable
data class BillDto (
    val title: String,
    val total: Double,
    val items: ArrayList<ItemDto>
)