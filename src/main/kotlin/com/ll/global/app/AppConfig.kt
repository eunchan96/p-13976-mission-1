package com.ll.global.app

object AppConfig {
    private var mode = "dev"

    val isDevMode: Boolean
        get() = mode == "dev"

    val isTestMode: Boolean
        get() = mode == "test"

    val isProdMode: Boolean
        get() = mode == "prod"

    fun setDevMode() { mode = "dev" }

    fun setTestMode() { mode = "test" }

    fun setProdMode() { mode = "prod" }
}