package com.ll.domain.wiseSaying.entity

data class WiseSaying(
    var id: Int = 0,
    var content: String,
    var author: String
) {
    constructor(wiseSayingMap: Map<String, Any?>) : this(
        id = wiseSayingMap["id"] as Int,
        content = wiseSayingMap["content"] as String,
        author = wiseSayingMap["author"] as String
    )

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "content" to content,
            "author" to author
        )
    }
}