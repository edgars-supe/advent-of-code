package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day4Test : DayTest<Int, Int>() {
    override val puzzle = { Day4() }

    @Test
    fun day4_1() {
        runTest("_1", 2, 2)
    }

    @Test
    fun day4_2() {
        runTest("_2", 4, 0)
    }

    @Test
    fun day4_3() {
        runTest("_3", 4, 4)
    }
}
