package com.ll

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    @DisplayName("== 명언 앱 == 출력")
    fun t1() {
        val output = AppTestRunner.run("")

        assertThat(output)
            .contains("== 명언 앱 ==")
            .contains("명령)")
    }
}