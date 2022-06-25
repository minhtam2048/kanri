package com.honito.controllers

import com.honito.dtos.ItemDto
import com.honito.models.Item
import com.honito.models.Items
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ItemController {

    fun getAllByBillId(billId: Int) : ArrayList<Item> {
        val items : ArrayList<Item> = arrayListOf()
        transaction {
            Items.select(where = Items.billId eq billId).map {
                items.add(
                    Item(
                        id = it[Items.id],
                        name = it[Items.name],
                        price = it[Items.price],
                        billId = it[Items.billId]
                    )
                )
            }
        }

        return items
    }

    fun createItem(item: ItemDto, billId: Int) {
        transaction {
            Items.insert {
                it[name] = item.name
                it[price] = item.price
                it[Items.billId] = billId
            }
        }
    }
}