package com.ll.standard.page

data class Pageable (
    val pageNum: Int = 1,
    val pageSize: Int = 5
) {
    fun getSkipCount(): Long {
        return (pageNum - 1) * pageSize.toLong()
    }
}