package com.spacecraft.hybrid

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform