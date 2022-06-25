package com.honito

import com.honito.models.Bills
import com.honito.models.Items
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseFactory {
    fun initDb() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://postgres:5432/kanri_db"
            driverClassName = "org.postgresql.Driver"
            username = "tamtam"
            password = "Luongminhtam123"
            maximumPoolSize = 10
        }
        val dataSource = HikariDataSource(config)
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_READ_COMMITTED
        Database.connect(dataSource)

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Bills, Items)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}
