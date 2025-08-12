package com.ll.global.app

import com.ll.domain.system.controller.SystemController
import com.ll.domain.wiseSaying.controller.WiseSayingController
import com.ll.domain.wiseSaying.repository.WiseSayingFileRepository
import com.ll.domain.wiseSaying.repository.WiseSayingMemoryRepository
import com.ll.domain.wiseSaying.repository.WiseSayingRepository
import com.ll.domain.wiseSaying.service.WiseSayingService

object AppContext {
    val wiseSayingRepository: WiseSayingRepository by lazy {
        when {
            AppConfig.isDevMode -> wiseSayingMemoryRepository
            AppConfig.isTestMode -> wiseSayingMemoryRepository
            AppConfig.isProdMode -> wiseSayingFileRepository
            else -> throw IllegalStateException("Unknown mode")
        }
    }

    val wiseSayingFileRepository by lazy { WiseSayingFileRepository() }
    val wiseSayingMemoryRepository by lazy { WiseSayingMemoryRepository() }
    val wiseSayingService by lazy { WiseSayingService() }
    val wiseSayingController by lazy { WiseSayingController() }
    val systemController by lazy { SystemController() }
}