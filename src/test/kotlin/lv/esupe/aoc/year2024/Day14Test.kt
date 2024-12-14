package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day14Test : DayTest<Int, Int>() {
    override val puzzle = { Day14(width = 11, height = 7) }

    @Test
    fun day14_1() {
        runTest("_1", 12, 1)
    }
}
