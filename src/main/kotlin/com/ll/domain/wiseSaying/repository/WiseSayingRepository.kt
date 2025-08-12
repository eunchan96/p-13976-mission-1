package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.page.Page
import com.ll.standard.page.Pageable

interface WiseSayingRepository {
    fun save(wiseSaying: WiseSaying): WiseSaying

    fun findById(id: Int): WiseSaying?

    fun delete(wiseSaying: WiseSaying): Boolean

    fun findForList(pageable: Pageable): Page<WiseSaying>

    fun findForListByContent(keyword: String, pageable: Pageable): Page<WiseSaying>

    fun findForListByAuthor(keyword: String, pageable: Pageable): Page<WiseSaying>

    fun findForListByContentOrAuthor(keyword: String, pageable: Pageable): Page<WiseSaying>

    fun clear()

    fun getTableDirPath(): String {
        return "db/wiseSaying"
    }

    fun build()
}