package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, Int>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest("_1", 36, 81)
    }
}
