package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day12Test : DayTest<Int, Int>() {
    override val puzzle = { Day12() }

    @Test
    fun day12_1() {
        runTest("_1", 10, 36)
    }

    @Test
    fun day12_2() {
        runTest("_2", 19, 103)
    }

    @Test
    fun day12_3() {
        runTest("_3", 226, 3509)
    }
}
