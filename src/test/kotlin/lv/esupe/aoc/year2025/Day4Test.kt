package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day4Test : DayTest<Int, Int>() {
    override val puzzle = { Day4() }

    @Test
    fun day4_1() {
        runTest("_1", 13, 43)
    }
}
