package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day6Test : DayTest<Int, Int>() {
    override val puzzle = { Day6() }

    @Test
    fun day6_1() {
        runTest("_1", 6, 3)
    }

    @Test
    fun day6_2() {
        runTest("_2", 11, 6)
    }
}
