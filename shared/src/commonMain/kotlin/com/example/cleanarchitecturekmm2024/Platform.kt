package com.example.cleanarchitecturekmm2024

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform