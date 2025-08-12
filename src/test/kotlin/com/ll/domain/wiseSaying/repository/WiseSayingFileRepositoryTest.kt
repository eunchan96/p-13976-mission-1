package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.page.Pageable
import com.ll.standard.util.Util
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class WiseSayingFileRepositoryTest {
    private val wiseSayingFileRepository = WiseSayingFileRepository()

    @BeforeEach
    fun beforeEach() {
        wiseSayingFileRepository.clear()
    }

    @Test
    @DisplayName("명언을 파일로 저장할 수 있다.")
    fun t1() {
        val wiseSaying1 = WiseSaying(content = "나의 죽음을 적에게 알리지 마라", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying1)

        val foundWiseSaying = wiseSayingFileRepository.findById(1)

        assertThat(foundWiseSaying).isEqualTo(wiseSaying1)
    }

    @Test
    @DisplayName("2번째 등록에서는 2번 명언이 생성된다.")
    fun t2() {
        val wiseSaying1 = WiseSaying(content = "호랑이 굴에 가야 호랑이 새끼를 잡는다.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 죽음을 적들에게 알리지 말라.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying2)

        assertThat(wiseSayingFileRepository.findById(2)).isEqualTo(wiseSaying2)
    }

    @Test
    @DisplayName("명언 삭제")
    fun t3() {
        val wiseSaying1 = WiseSaying(content = "호랑이 굴에 가야 호랑이 새끼를 잡는다.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 죽음을 적들에게 알리지 말라.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying2)

        wiseSayingFileRepository.delete(wiseSaying1)

        assertThat(wiseSayingFileRepository.findById(1)).isNull()
    }

    @Test
    @DisplayName("명언 다건 조회")
    fun t4() {
        val wiseSaying1 = WiseSaying(content = "호랑이 굴에 가야 호랑이 새끼를 잡는다.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 죽음을 적들에게 알리지 말라.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying2)

        assertThat(
            wiseSayingFileRepository.findForList(Pageable(1, 5)).content
        ).containsExactly(wiseSaying2, wiseSaying1)
    }

    @Test
    @DisplayName("명언 다건조회, Content로 조회")
    fun t5() {
        val wiseSaying1 = WiseSaying(content = "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", author = "괴테")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 죽음을 적들에게 알리지 말라.", author = "이순신")
        wiseSayingFileRepository.save(wiseSaying2)

        val wiseSaying3 = WiseSaying(content = "생생한 꿈은 현실이 된다.", author = "작자미상")
        wiseSayingFileRepository.save(wiseSaying3)

        assertThat(
            wiseSayingFileRepository.findForListByContent("꿈", Pageable(1, 5)).content
        ).containsExactly(wiseSaying3, wiseSaying1)
    }

    @Test
    @DisplayName("명언 다건조회, Author로 조회")
    fun t6() {
        val wiseSaying1 = WiseSaying(content = "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", author = "괴테")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "내가 누구게", author = "작자미상")
        wiseSayingFileRepository.save(wiseSaying2)

        val wiseSaying3 = WiseSaying(content = "생생한 꿈은 현실이 된다.", author = "작자미상")
        wiseSayingFileRepository.save(wiseSaying3)

        assertThat(
            wiseSayingFileRepository.findForListByAuthor("작자미상", Pageable(1, 5)).content
        ).containsExactly(wiseSaying3, wiseSaying2)
    }

    @Test
    @DisplayName("명언 다건조회, findForListByContentContainingOrAuthorContaining")
    fun t7() {
        val wiseSaying1 = WiseSaying(content = "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", author = "괴테")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 삶의 가치는 나의 결정에 달려있다.", author = "아인슈타인")
        wiseSayingFileRepository.save(wiseSaying2)

        val wiseSaying3 = WiseSaying(content = "생생한 꿈은 현실이 된다.", author = "작자미상")
        wiseSayingFileRepository.save(wiseSaying3)

        val wiseSaying4 = WiseSaying(content = "신은 주사위놀이를 하지 않는다.", author = "아인슈타인")
        wiseSayingFileRepository.save(wiseSaying4)

        val wiseSaying5 = WiseSaying(content = "나의 상상은 현실이 된다.", author = "아무개")
        wiseSayingFileRepository.save(wiseSaying5)

        assertThat(
            wiseSayingFileRepository.findForListByContentOrAuthor("상", Pageable(1, 5)).content
        ).containsExactly(wiseSaying5, wiseSaying3)
    }

    @Test
    @DisplayName("빌드를 하면 data.json 파일이 생성된다.")
    fun t8() {
        val wiseSaying1 = WiseSaying(content = "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", author = "괴테")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 삶의 가치는 나의 결정에 달려있다.", author = "아인슈타인")
        wiseSayingFileRepository.save(wiseSaying2)

        wiseSayingFileRepository.build()

        assertThat(Util.FileUtil.exists("db/wiseSaying/data.json")).isTrue()
    }

    @Test
    @DisplayName("빌드 시 생성되는 data.json은 배열의 형태이다.")
    fun t9() {
        val wiseSaying1 = WiseSaying(content = "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", author = "괴테")
        wiseSayingFileRepository.save(wiseSaying1)

        val wiseSaying2 = WiseSaying(content = "나의 삶의 가치는 나의 결정에 달려있다.", author = "아인슈타인")
        wiseSayingFileRepository.save(wiseSaying2)

        wiseSayingFileRepository.build()

        val jsonStr = Util.FileUtil.get("db/wiseSaying/data.json")

        assertThat(jsonStr).isEqualTo(
            """
            [
                {
                    "id": 1,
                    "content": "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.",
                    "author": "괴테"
                },
                {
                    "id": 2,
                    "content": "나의 삶의 가치는 나의 결정에 달려있다.",
                    "author": "아인슈타인"
                }
            ]
            """.trimIndent()
        )
    }
}