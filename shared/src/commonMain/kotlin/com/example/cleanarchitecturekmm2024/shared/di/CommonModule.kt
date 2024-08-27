package com.example.cleanarchitecturekmm2024.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule (enableNetworkLogs: Boolean) = module {

    //networking
    single {

    }
    // repository

    //dao

    //viewModel
}

expect fun platformModule(): Module