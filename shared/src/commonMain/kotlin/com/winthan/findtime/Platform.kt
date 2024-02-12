package com.winthan.findtime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform