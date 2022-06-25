package com.honito.routes

import com.honito.controllers.BillController
import com.honito.controllers.ItemController
import com.honito.dtos.BillDto
import com.honito.models.Bill
import com.honito.models.Bills
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun Route.billRoutes() {
    val billController = BillController()

    route("/bills") {
        get {
            call.respond(HttpStatusCode.OK, billController.getAll())

        }

        get("/bills/{id?}") {
            val params = call.receiveParameters()
            val id = params.getOrFail("id").toInt()
            call.respond(HttpStatusCode.OK, billController.getBillById(id))
        }

        post {
            val billDto = call.receive<BillDto>()
            val bill = billController.insertBill(billDto)
            call.respond(HttpStatusCode.Created, bill)
        }

        delete("{id?}") {

        }
    }
}