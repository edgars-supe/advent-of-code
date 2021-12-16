package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.BitSet

class Day16Test : DayTest<Long, Long>() {
    override val puzzle = { Day16() }

    @Test
    fun day16_1() {
        runTest("_1", 6L, 2021L)
    }

    @Test
    fun day16_2() {
        runTest("_2", 9L, 1L)
    }

    @Test
    fun day16_3() {
        runTest("_3", 14L, 3L)
    }

    @Test
    fun day16_4() {
        runTest("_4", 16L, 15L)
    }

    @Test
    fun day16_5() {
        runTest("_5", 12L, 46L)
    }

    @Test
    fun day16_6() {
        runTest("_6", 23L, 46L)
    }

    @Test
    fun day16_7() {
        runTest("_7", 31L, 54L)
    }
}
