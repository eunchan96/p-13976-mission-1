package com.ll.domain.wiseSaying.controller

import com.ll.AppTestRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    fun t1() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
        """)

        assertThat(output)
            .contains("명언 :")
            .contains("작가 :")
    }

    @Test
    @DisplayName("등록 시 명언 번호 출력")
    fun t2() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
        """)

        assertThat(output)
            .contains("1번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("등록 시 명언 번호 증가")
    fun t3() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            등록
            내 사전에 불가능이란 없다.
            나폴레옹
        """)

        assertThat(output)
            .contains("1번 명언이 등록되었습니다.")
            .contains("2번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("목록")
    fun t4() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            등록
            내 사전에 불가능이란 없다.
            나폴레옹
            목록
        """)

        assertThat(output)
            .contains("번호 / 작가 / 명언")
            .contains("2 / 나폴레옹 / 내 사전에 불가능이란 없다.")
            .contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
    }

    @Test
    @DisplayName("삭제")
    fun t5() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            등록
            내 사전에 불가능이란 없다.
            나폴레옹
            삭제?id=1
            목록
        """)

        assertThat(output)
            .contains("1번 명언이 삭제되었습니다.")
            .contains("번호 / 작가 / 명언")
            .contains("2 / 나폴레옹 / 내 사전에 불가능이란 없다.")
            .doesNotContain("1 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
    }

    @Test
    @DisplayName("삭제 예외처리")
    fun t6() {
        val output = AppTestRunner.run("""
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            삭제?id=1
            삭제?id=1
        """)

        assertThat(output)
            .contains("1번 명언이 삭제되었습니다.")
            .contains("1번 명언은 존재하지 않습니다.")
    }

    @Test
    @DisplayName("수정")
    fun t7() {
        val output = AppTestRunner.run("""
            등록
            현재를 사랑하라.
            작자미상
            수정?id=3
            수정?id=1
            현재를 사랑하세요.
            아리스토텔레스
            목록
        """)

        assertThat(output)
            .contains("3번 명언은 존재하지 않습니다.")
            .contains("명언(기존) : 현재를 사랑하라.")
            .contains("작가(기존) : 작자미상")
            .contains("1 / 아리스토텔레스 / 현재를 사랑하세요.")
            .doesNotContain("1 / 작지미상 / 현재를 사랑하라.")
    }

    @Test
    @DisplayName("목록 검색 - author")
    fun t8() {
        val output = AppTestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 마라.
            작가미상
            등록
            내 사전에 불가능이란 없다.
            작가미상
            목록?keywordType=author&keyword=작가
            """)

        assertThat(output)
            .contains("----------------------")
            .contains("검색타입 : author")
            .contains("검색어 : 작가")
            .contains("----------------------")
            .contains("2 / 작가미상 / 내 사전에 불가능이란 없다.")
            .contains("1 / 작가미상 / 나의 죽음을 적들에게 알리지 마라.")
    }

    @Test
    @DisplayName("목록 검색 - content")
    fun t9() {
        val output = AppTestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 마라.
            작가미상
            등록
            내 사전에 불가능이란 없다.
            작가미상
            목록?keywordType=content&keyword=불가능
            """)

        assertThat(output)
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 불가능")
            .contains("----------------------")
            .contains("2 / 작가미상 / 내 사전에 불가능이란 없다.")
            .doesNotContain("1 / 작가미상 / 나의 죽음을 적들에게 알리지 마라.")
    }

    @Test
    @DisplayName("목록 검색 - without keywordType")
    fun t10() {
        val output = AppTestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            등록
            내 사전에 불가능이란 없다.
            나폴레용
            목록?keyword=이순신
            """)

        assertThat(output)
            .contains("----------------------")
            .contains("검색타입 : all")
            .contains("검색어 : 이순신")
            .contains("----------------------")
            .doesNotContain("2 / 나폴레옹 / 내 사전에 불가능이란 없다.")
            .contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
    }

    fun makeSampleData(): String {
        return (1..10).joinToString("\n") {
            "등록\n명언 $it\n작가미상 $it"
        }
    }

    @Test
    @DisplayName("목록?page=1")
    fun t11() {
        val output = AppTestRunner.run("${makeSampleData()}\n목록?page=1")

        assertThat(output)
            .contains("10 / 작가미상 10 / 명언 10")
            .contains("9 / 작가미상 9 / 명언 9")
            .contains("8 / 작가미상 8 / 명언 8")
            .contains("7 / 작가미상 7 / 명언 7")
            .contains("6 / 작가미상 6 / 명언 6")
            .doesNotContain("5 / 작가미상 5 / 명언 5")
            .doesNotContain("4 / 작가미상 4 / 명언 4")
            .doesNotContain("3 / 작가미상 3 / 명언 3")
            .doesNotContain("2 / 작가미상 2 / 명언 2")
            .doesNotContain("1 / 작가미상 1 / 명언 1")
            .contains("페이지 : [1] / 2")
    }

    @Test
    @DisplayName("목록?page=2")
    fun t12() {
        val output = AppTestRunner.run("${makeSampleData()}\n목록?page=2")

        assertThat(output)
            .doesNotContain("10 / 작가미상 10 / 명언 10")
            .doesNotContain("9 / 작가미상 9 / 명언 9")
            .doesNotContain("8 / 작가미상 8 / 명언 8")
            .doesNotContain("7 / 작가미상 7 / 명언 7")
            .doesNotContain("6 / 작가미상 6 / 명언 6")
            .contains("5 / 작가미상 5 / 명언 5")
            .contains("4 / 작가미상 4 / 명언 4")
            .contains("3 / 작가미상 3 / 명언 3")
            .contains("2 / 작가미상 2 / 명언 2")
            .contains("1 / 작가미상 1 / 명언 1")
            .contains("페이지 : 1 / [2]")
    }

    @Test
    @DisplayName("목록?keyword=명언&page=2")
    fun t13() {
        val output = AppTestRunner.run("${makeSampleData()}\n목록?keyword=명언&page=2")

        assertThat(output)
            .doesNotContain("10 / 작가미상 10 / 명언 10")
            .doesNotContain("9 / 작가미상 9 / 명언 9")
            .doesNotContain("8 / 작가미상 8 / 명언 8")
            .doesNotContain("7 / 작가미상 7 / 명언 7")
            .doesNotContain("6 / 작가미상 6 / 명언 6")
            .contains("5 / 작가미상 5 / 명언 5")
            .contains("4 / 작가미상 4 / 명언 4")
            .contains("3 / 작가미상 3 / 명언 3")
            .contains("2 / 작가미상 2 / 명언 2")
            .contains("1 / 작가미상 1 / 명언 1")
            .contains("페이지 : 1 / [2]")
    }

    @Test
    @DisplayName("목록?keyword=명언 1")
    fun t14() {
        val output = AppTestRunner.run("${makeSampleData()}\n목록?keyword=명언 1")

        assertThat(output)
            .contains("10 / 작가미상 10 / 명언 10")
            .doesNotContain("9 / 작가미상 9 / 명언 9")
            .doesNotContain("8 / 작가미상 8 / 명언 8")
            .doesNotContain("7 / 작가미상 7 / 명언 7")
            .doesNotContain("6 / 작가미상 6 / 명언 6")
            .doesNotContain("5 / 작가미상 5 / 명언 5")
            .doesNotContain("4 / 작가미상 4 / 명언 4")
            .doesNotContain("3 / 작가미상 3 / 명언 3")
            .doesNotContain("2 / 작가미상 2 / 명언 2")
            .contains("1 / 작가미상 1 / 명언 1")
            .contains("페이지 : [1]")
    }

    @Test
    @DisplayName("빌드")
    fun t15() {
        val output = AppTestRunner.run(
            """
            등록
            나의 죽음을 적들에게 알리지 마라.
            이순신
            등록
            내 사전에 불가능이란 없다.
            나폴레용
            빌드
            """)

        assertThat(output)
            .contains("data.json 파일의 내용이 갱신되었습니다.")
    }
}