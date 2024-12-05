package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day5Test : DayTest<Int, Int>() {
    override val puzzle = { Day5() }

    @Test
    fun day5_1() {
        runTest("_1", 143, 0)
    }
}
