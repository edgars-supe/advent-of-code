package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day5Test : DayTest<Int, Long>() {
    override val puzzle = { Day5() }

    @Test
    fun day5_1() {
        runTest("_1", 3, 14)
    }
}
