package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day2Test : DayTest<Int, Int>() {
    override val puzzle = { Day2() }

    @Test
    fun day2_1() {
        runTest("_1", 2, 4)
    }

    @Test
    fun day2_2() {
        runTest("_2", 2, 6)
    }

    @Test
    fun day2_3() {
        runTest("_3", 0, 24)
    }

    @Test
    fun day2_4() {
        runTest("_4", 0, 2)
    }
}
