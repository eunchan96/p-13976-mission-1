package com.ll.standard.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class JsonUtilTest {
    @Test
    @DisplayName("맵을 JSON으로 바꿀 수 있다.(필드 1개)")
    fun t1() {
        // given
        val map = hashMapOf<String, Any?>("name" to "이름")

        // when
        val jsonStr = Util.JsonUtil.toString(map)

        // then
        assertThat(jsonStr).isEqualTo(
            """
            {
                "name": "이름"
            }
            """.trimIndent()
        )
    }

    @Test
    @DisplayName("맵을 JSON으로 바꿀 수 있다.(필드 2개)")
    fun t2() {
        // given
        val map = linkedMapOf<String, Any?>(
            "name" to "이름",
            "gender" to "남자"
        )

        // when
        val jsonStr = Util.JsonUtil.toString(map)

        // then
        assertThat(jsonStr).isEqualTo(
            """
            {
                "name": "이름",
                "gender": "남자"
            }
            """.trimIndent()
        )
    }

    @Test
    @DisplayName("맵을 JSON으로 바꿀 수 있다.(숫자(정수) 필드)")
    fun t3() {
        // given
        val map = linkedMapOf<String, Any?>(
            "id" to 1,
            "name" to "이름",
            "gender" to "남자"
        )

        // when
        val jsonStr = Util.JsonUtil.toString(map)

        // then
        assertThat(jsonStr).isEqualTo(
            """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자"
            }
            """.trimIndent()
        )
    }

    @Test
    @DisplayName("맵을 JSON으로 바꿀 수 있다.(숫자(실수) 필드)")
    fun t4() {
        // given
        val map = linkedMapOf<String, Any?>(
            "id" to 1,
            "name" to "이름",
            "gender" to "남자",
            "height" to 178.1543221
        )

        // when
        val jsonStr = Util.JsonUtil.toString(map)

        // then
        assertThat(jsonStr).isEqualTo(
            """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자",
                "height": 178.1543221
            }
            """.trimIndent()
        )
    }

    @Test
    @DisplayName("맵을 JSON으로 바꿀 수 있다.(논리 필드)")
    fun t5() {
        // given
        val map = linkedMapOf<String, Any?>(
            "id" to 1,
            "name" to "이름",
            "gender" to "남자",
            "height" to 178.1543221,
            "married" to true
        )

        // when
        val jsonStr = Util.JsonUtil.toString(map)

        // then
        assertThat(jsonStr).isEqualTo(
            """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자",
                "height": 178.1543221,
                "married": true
            }
            """.trimIndent()
        )
    }

    @Test
    @DisplayName("JSON to Map(필드 1개)")
    fun t6() {
        // given
        val jsonStr = """
            {
                "name": "이름",
            }
            """.trimIndent()

        // when
        val map = Util.JsonUtil.toMap(jsonStr)

        // then
        assertThat(map).containsEntry("name", "이름")
    }

    @Test
    @DisplayName("JSON to Map(필드 2개)")
    fun t7() {
        // given
        val jsonStr = """
            {
                "name": "이름",
                "gender": "남자"
            }
            """.trimIndent()

        // when
        val map = Util.JsonUtil.toMap(jsonStr)

        // then
        assertThat(map)
            .containsEntry("name", "이름")
            .containsEntry("gender", "남자")
    }

    @Test
    @DisplayName("JSON to Map(숫자필드(정수))")
    fun t8() {
        // given
        val jsonStr = """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자"
            }
            """.trimIndent()

        // when
        val map = Util.JsonUtil.toMap(jsonStr)

        // then
        assertThat(map)
            .containsEntry("id", 1)
            .containsEntry("name", "이름")
            .containsEntry("gender", "남자")
    }

    @Test
    @DisplayName("JSON to Map(숫자필드(실수))")
    fun t9() {
        // given
        val jsonStr = """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자",
                "height": 178.1543221
            }
            """.trimIndent()

        // when
        val map = Util.JsonUtil.toMap(jsonStr)

        // then
        assertThat(map)
            .containsEntry("id", 1)
            .containsEntry("name", "이름")
            .containsEntry("gender", "남자")
            .containsEntry("height", 178.1543221)
    }

    @Test
    @DisplayName("JSON to Map(논리필드)")
    fun t10() {
        // given
        val jsonStr = """
            {
                "id": 1,
                "name": "이름",
                "gender": "남자",
                "height": 178.1543221,
                "married": false
            }
            """.trimIndent()

        // when
        val map = Util.JsonUtil.toMap(jsonStr)

        // then
        assertThat(map)
            .containsEntry("id", 1)
            .containsEntry("name", "이름")
            .containsEntry("gender", "남자")
            .containsEntry("height", 178.1543221)
            .containsEntry("married", false)
    }
}