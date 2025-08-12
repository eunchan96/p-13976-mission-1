package com.ll.global.rq

class Rq {
    private val actionName: String
    private val params: Map<String, String>

    constructor(cmd: String) {
        val cmdBits = cmd.split('?', limit = 2)
        actionName = cmdBits[0].trim()
        val queryString = if (cmdBits.size == 2) cmdBits[1] else ""

        params = queryString.split('&')
            .mapNotNull { it.split('=', limit = 2).takeIf { it -> it.size == 2 && it[0].isNotBlank() && it[1].isNotBlank() } }
            .associate { (key, value) -> key.trim() to value.trim() }
    }

    fun getParam(paramName: String, defaultValue: String): String {
        return params[paramName] ?: defaultValue
    }

    fun getParamAsInt(paramName: String, defaultValue: Int): Int {
        val value = getParam(paramName, "")
        if (value.isBlank()) return defaultValue

        try {
            return Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            return defaultValue
        }
    }

    fun getActionName(): String {
        return actionName
    }
}