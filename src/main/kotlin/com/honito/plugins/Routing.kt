package com.honito.plugins

import com.honito.routes.billRoutes
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureRouting() {

    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }

        billRoutes()
    }
}
