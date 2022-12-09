package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day9Test : DayTest<Int, Int>() {
    override val puzzle = { Day9() }

    @Test
    fun day9_1() {
        runTest("_1", 13, 1)
    }

    @Test
    fun day9_2() {
        runTest("_2", 88, 36)
    }
}
