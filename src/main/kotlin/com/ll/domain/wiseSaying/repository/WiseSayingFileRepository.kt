package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.page.Page
import com.ll.standard.page.Pageable
import com.ll.standard.util.Util

class WiseSayingFileRepository : WiseSayingRepository {
    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.id == 0) {
            wiseSaying.id = getLastId() + 1
            setLastId(wiseSaying.id)
        }
        val wiseSayingMap = wiseSaying.toMap()
        val wiseSayingJsonStr = Util.JsonUtil.toString(wiseSayingMap)
        Util.FileUtil.set("${getTableDirPath()}/${wiseSaying.id}.json", wiseSayingJsonStr)
        return wiseSaying
    }

    private fun getLastId(): Int {
        return Util.FileUtil.getAsInt("${getTableDirPath()}/lastId.txt", 0)
    }

    private fun setLastId(newId: Int) {
        Util.FileUtil.set("${getTableDirPath()}/lastId.txt", newId)
    }

    override fun findById(id: Int): WiseSaying? {
        val wiseSayingJsonStr = Util.FileUtil.get("${getTableDirPath()}/$id.json")
        if (wiseSayingJsonStr.isBlank()) return null
        val wiseSayingMap = Util.JsonUtil.toMap(wiseSayingJsonStr)
        return WiseSaying(wiseSayingMap)
    }

    override fun delete(wiseSaying: WiseSaying): Boolean {
        return Util.FileUtil.delete("${getTableDirPath()}/${wiseSaying.id}.json")
    }

    private fun createPage(items: List<WiseSaying>, pageable: Pageable): Page<WiseSaying> {
        val totalCount: Int = items.size

        return items.reversed()
            .drop(pageable.getSkipCount().toInt())
            .take(pageable.pageSize)
            .let { Page(totalCount, pageable.pageNum, pageable.pageSize, it) }
    }

    override fun findForList(pageable: Pageable): Page<WiseSaying> {
        val wiseSayings = findAll()
        return createPage(wiseSayings, pageable)
    }

    private fun findAll(): List<WiseSaying> {
        return Util.FileUtil.walkRegularFiles(getTableDirPath(), "\\d+\\.json")
            .map { file -> Util.FileUtil.get(file.path) }
            .map { jsonStr -> Util.JsonUtil.toMap(jsonStr) }
            .map { map -> WiseSaying(map) }
            .toList()
    }

    override fun findForListByContent(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = findAll().filter { it.content.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun findForListByAuthor(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = findAll().filter { it.author.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun findForListByContentOrAuthor(keyword: String, pageable: Pageable): Page<WiseSaying> {
        val filtered = findAll().filter { it.author.contains(keyword) || it.content.contains(keyword) }
        return createPage(filtered, pageable)
    }

    override fun clear() {
        Util.FileUtil.rmdir(getTableDirPath())
    }

    override fun build() {
        val map = findAll().map { it.toMap() }
        val json = Util.JsonUtil.toString(map)
        Util.FileUtil.set("${getTableDirPath()}/data.json", json)
    }
}