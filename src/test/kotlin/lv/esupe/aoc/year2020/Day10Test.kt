package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, Long>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest("_1", 35, 8)
    }

    @Test
    fun day10_2() {
        runTest("_2", 220, 19208)
    }

    @Test
    fun day10_3() {
        runTest("_3", 5, 1)
    }

    @Test
    fun day10_4() {
        runTest("_4", 8, 2)
    }
}
