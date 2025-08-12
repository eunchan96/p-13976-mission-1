package com.ll.standard.page

data class Page<T>(
    val totalCount: Int,
    val pageNum: Int,
    val pageSize: Int,
    val content: List<T>
) {
    val totalPages: Int
        get() = (totalCount.toDouble() / pageSize).ceil().toInt()
}

fun Double.ceil() = kotlin.math.ceil(this)