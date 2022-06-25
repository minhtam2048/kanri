package com.honito

import com.honito.DatabaseFactory.initDb
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.honito.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        initDb()
        configureRouting()
        configureHTTP()
    }.start(wait = true)
}
