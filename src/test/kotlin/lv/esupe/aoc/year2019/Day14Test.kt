package lv.esupe.aoc.year2019

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test


class Day14Test : DayTest<Long, Long>() {

    override val puzzle = { Day14() }

    @Test
    fun day14_1() {
        runTest("_1", 31, 34482758620)
    }

    @Test
    fun day14_2() {
        runTest("_2", 165, 6323777403)
    }

    @Test
    fun day14_3() {
        runTest("_3", 13312, 82892753)
    }

    @Test
    fun day14_4() {
        runTest("_4", 180697, 5586022)
    }

    @Test
    fun day14_5() {
        runTest("_5", 2210736, 460664)
    }
}