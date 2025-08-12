package com.ll

import com.ll.global.app.AppContext.systemController
import com.ll.global.app.AppContext.wiseSayingController
import com.ll.global.rq.Rq

class App {
    fun run() {
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val cmd = readlnOrNull()!!.trim()
            val rq = Rq(cmd)

            when (rq.getActionName()) {
                "종료" -> {
                    systemController.exit()
                    return
                }
                "등록" -> wiseSayingController.create()
                "목록" -> wiseSayingController.list(rq)
                "삭제" -> wiseSayingController.delete(rq)
                "수정" -> wiseSayingController.modify(rq)
                "빌드" -> wiseSayingController.build()
                else -> println("알 수 없는 명령입니다.")
            }
        }
    }
}