package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.page.Page
import com.ll.standard.page.Pageable

class WiseSayingMemoryRepository : WiseSayingRepository {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.id == 0) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
        }
        return wiseSaying
    }

    override fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    override fun delete(wiseSaying: WiseSaying): Boolean {
        return wiseSayings.remove(wiseSaying)
    }

    private fun createPage(items: List<WiseSaying>, pageable: Pageable): Page<WiseSaying> {
        val totalCount: Int = items.size

        return items.reversed()
            .drop(pageable.getSkipCount().toInt())
            .take(pageable.pageSize)
            .let { Page(totalCount, pageable.pageNum, pageable.pageSize, it) }
    }

    override fun findForList(pageable: Pageable): Page<WiseSaying> {
        return createPage(wiseSayings, pageable)
    }

    override fun findForListByContent(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = wiseSayings.filter { it.content.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun findForListByAuthor(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = wiseSayings.filter { it.author.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun findForListByContentOrAuthor(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = wiseSayings.filter { it.author.contains(keyword) || it.content.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }

    override fun build() {}
}