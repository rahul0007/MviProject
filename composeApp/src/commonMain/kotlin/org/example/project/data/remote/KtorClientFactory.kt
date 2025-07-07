package org.example.project.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClientFactory {
    fun create(): HttpClient {
        return HttpClient {
            // JSON serialization
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // Timeout configuration
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }

            // Logging configuration
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("KTOR LOG => $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}

