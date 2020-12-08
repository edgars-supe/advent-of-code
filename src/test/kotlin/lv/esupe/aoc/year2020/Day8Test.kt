package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day8Test : DayTest<Int, Int>() {
    override val puzzle = { Day8() }

    @Test
    fun day8_1() {
        runTest("_1", 5, 8)
    }
}
