package com.ll.domain.wiseSaying.controller

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.global.app.AppContext.wiseSayingService
import com.ll.global.rq.Rq
import com.ll.standard.page.Page
import com.ll.standard.page.Pageable

class WiseSayingController {
    fun create() {
        print("명언 : ")
        val content = readlnOrNull()!!.trim()
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        val wiseSaying = wiseSayingService.create(content, author)

        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun list(rq: Rq) {
        val pageNum = rq.getParamAsInt("page", 1)
        val pageSize = rq.getParamAsInt("pageSize", 5)
        val pageable = Pageable(pageNum, pageSize)

        val keywordType = rq.getParam("keywordType", "all")
        val keyword = rq.getParam("keyword", "")

        val wiseSayingPage: Page<WiseSaying> = wiseSayingService.findForList(pageable, keywordType, keyword)

        if (keyword.isNotBlank()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
        }

        println("번호 / 작가 / 명언")
        println("----------------------")

        for (wiseSaying in wiseSayingPage.content) {
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }

        print("페이지 : ")
        val pageMenu = (1..wiseSayingPage.totalPages).joinToString(" / ") {
            i -> if (i == wiseSayingPage.pageNum) "[$i]" else "$i"
        }
        println(pageMenu)
    }

    fun delete(rq: Rq) {
        val id = rq.getParamAsInt("id", -1)
        if (id <= 0) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val deleted = wiseSayingService.delete(id)
        if (deleted) println("${id}번 명언이 삭제되었습니다.")
        else println("${id}번 명언은 존재하지 않습니다.")
    }

    fun modify(rq: Rq) {
        val id = rq.getParamAsInt("id", -1)
        if (id <= 0) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.findById(id) ?: run {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val content = readlnOrNull()!!.trim()

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        wiseSayingService.modify(wiseSaying, content, author)
    }

    fun build() {
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}