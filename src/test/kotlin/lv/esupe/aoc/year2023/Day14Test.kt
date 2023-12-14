package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day14Test : DayTest<Int, Int>() {
    override val puzzle = { Day14() }

    @Test
    fun day14_1() {
        runTest("_1", 136, 64)
    }
}
