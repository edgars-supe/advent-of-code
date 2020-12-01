package lv.esupe.aoc.year2019

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, Int>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest("_1", 8, -2)
    }

    @Test
    fun day10_2() {
        runTest("_2", 33, -2)
    }

    @Test
    fun day10_3() {
        runTest("_3", 35, -2)
    }

    @Test
    fun day10_4() {
        runTest("_4", 41, -2)
    }

    @Test
    fun day10_5() {
        runTest("_5", 210, -2)
    }
}
