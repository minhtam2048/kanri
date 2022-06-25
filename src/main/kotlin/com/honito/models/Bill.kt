package com.honito.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Bill(val id: Int, val title: String, val total: Double, val items: Collection<Item>)

object Bills : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val total = double("total")

    override val primaryKey = PrimaryKey(id, name = "PK_Bills_ID")
}