package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day11Test : DayTest<Int, Int>() {
    override val puzzle = { Day11() }

    @Test
    fun day11_1() {
        runTest("_1", 1656, 195)
    }
}
