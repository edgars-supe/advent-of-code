package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day1Test : DayTest<Int, Int>() {
    override val puzzle = { Day1() }

    @Test
    fun day1_1() {
        runTest("_1", 11, 31)
    }
}
