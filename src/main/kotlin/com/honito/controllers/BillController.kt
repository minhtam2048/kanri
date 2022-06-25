package com.honito.controllers

import com.honito.dtos.BillDto
import com.honito.dtos.ItemDto
import com.honito.models.Bill
import com.honito.models.Bills
import com.honito.models.Items
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class BillController {

    private val itemController = ItemController()

    fun getAll(): ArrayList<Bill> {
        val bills: ArrayList<Bill> = arrayListOf()
        transaction {
            Bills.selectAll().map {
                bills.add(
                    Bill(
                        id = it[Bills.id],
                        title = it[Bills.title],
                        total = it[Bills.total],
                        items  = itemController.getAllByBillId(it[Bills.id])
                    )
                )
            }
        }

        return bills
    }

    fun getBillById(id: Int) : Bill {
        val bill : Bill = transaction {
            Bills.select(where = Bills.id eq id).map { _bill ->
                Bill (
                    id = _bill[Bills.id],
                    title = _bill[Bills.title],
                    total = _bill[Bills.total],
                    items = itemController.getAllByBillId(_bill[Bills.id])
                )
            }.first()
        }

        return bill
    }

    fun insertBill(billDto: BillDto) : Bill {
        return transaction {
            val billId = Bills.insert {
                it[title] = billDto.title
                it[total] = billDto.total
            } get Bills.id

            billDto.items.map { _item: ItemDto ->
                itemController.createItem(_item, billId)
            }

            return@transaction getBillById(billId)
        }
    }
}