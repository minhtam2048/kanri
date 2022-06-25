package com.honito.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Item(val id: Int, val name: String, val price: Double, val billId: Int)

object Items : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val price = double("price")

    val billId = (integer("bill_id") references Bills.id)

    override val primaryKey = PrimaryKey(id, name="PK_Items_ID")
}
