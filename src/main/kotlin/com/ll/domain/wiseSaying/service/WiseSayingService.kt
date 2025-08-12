package com.ll.domain.wiseSaying.service

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.global.app.AppContext.wiseSayingRepository
import com.ll.standard.page.Page
import com.ll.standard.page.Pageable

class WiseSayingService {
    fun create(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(content = content, author = author)
        return wiseSayingRepository.save(wiseSaying)
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun delete(id: Int): Boolean {
        val wiseSaying = wiseSayingRepository.findById(id) ?: return false
        return wiseSayingRepository.delete(wiseSaying)
    }

    fun modify(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.content = content
        wiseSaying.author = author

        wiseSayingRepository.save(wiseSaying)
    }

    fun findForList(pageable: Pageable, keywordType: String, keyword: String): Page<WiseSaying> {
        if (keyword == "") return wiseSayingRepository.findForList(pageable)
        return when (keywordType) {
            "content" -> wiseSayingRepository.findForListByContent(keyword, pageable)
            "author" -> wiseSayingRepository.findForListByAuthor(keyword, pageable)
            else -> wiseSayingRepository.findForListByContentOrAuthor(keyword, pageable)
        }
    }

    fun build() {
        wiseSayingRepository.build()
    }
}