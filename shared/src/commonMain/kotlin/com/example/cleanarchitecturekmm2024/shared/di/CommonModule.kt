package com.example.cleanarchitecturekmm2024.shared.di

import com.example.cleanarchitecturekmm2024.BuildKonfig
import com.example.cleanarchitecturekmm2024.shared.utils.Constanst.URL_PATH
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import org.koin.core.module.Module
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun commonModule (enableNetworkLogs: Boolean) = module {

    //networking
    single {
        HttpClient {
            expectSuccess = true
            addDefaultResponseValidation()

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildKonfig.BASE_URL
                    path(URL_PATH)
                    parameters.append("api_key", BuildKonfig.API_KEY)
                }
            }

            if (enableNetworkLogs) {
                install(Logging) {
                    level = LogLevel.HEADERS
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.i(tag = "Http Client", message = message)
                        }
                    }
                }.also {
                    Napier.base(DebugAntilog())
                }
            }

            install(ContentNegotiation) {
                json(
                    Json{
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    }
    // repository

    //dao

    //viewModel
}

expect fun platformModule(): Module