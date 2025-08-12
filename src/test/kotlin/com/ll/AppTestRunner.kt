package com.ll

import com.ll.global.app.AppConfig
import com.ll.global.app.AppContext
import com.ll.standard.util.TestUtil

object AppTestRunner {
    fun run(input: String): String {
        TestUtil.setInToByteArray("${input}\n종료")
        val output = TestUtil.setOutToByteArray()

        AppConfig.setTestMode()
        AppContext.wiseSayingRepository.clear()

        App().run()

        return output.toString().trim()
            .also { TestUtil.clearSetOutToByteArray(output) }
    }
}