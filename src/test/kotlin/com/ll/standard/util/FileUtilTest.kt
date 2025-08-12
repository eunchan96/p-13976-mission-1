package com.ll.standard.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FileUtilTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Util.FileUtil.mkdir("temp")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            Util.FileUtil.rmdir("temp")
        }
    }

    @Test
    @DisplayName("파일을 생성할 수 있다.")
    fun t1() {
        // given
        val filePath = "temp/test.txt"

        // when
        Util.FileUtil.touch(filePath)

        // then
        assertThat(Util.FileUtil.exists(filePath)).isTrue()
        Util.FileUtil.delete(filePath)
    }

    @Test
    @DisplayName("파일의 내용을 수정할 수 있고, 읽을 수 있다.")
    fun t2() {
        // given
        val filePath = "temp/test.txt"

        // when
        Util.FileUtil.set(filePath, "내용")

        // then
        assertThat(Util.FileUtil.exists(filePath)).isTrue()
        assertThat(Util.FileUtil.get(filePath)).isEqualTo("내용")
        Util.FileUtil.delete(filePath)
    }

    @Test
    @DisplayName("파일을 삭제할 수 있다.")
    fun t3() {
        // given
        val filePath = "temp/test.txt"

        // when
        Util.FileUtil.touch(filePath)
        Util.FileUtil.delete(filePath)

        // then
        assertThat(Util.FileUtil.notExists(filePath)).isTrue()
    }

    @Test
    @DisplayName("파일을 생성할 수 있다, 만약 해당 경로의 폴더가 없다면 만든다.")
    fun t4() {
        // given
        val filePath = "temp/temp/test.txt"

        // when
        Util.FileUtil.touch(filePath)

        // then
        assertThat(Util.FileUtil.exists(filePath)).isTrue()
        Util.FileUtil.delete(filePath)
    }
}