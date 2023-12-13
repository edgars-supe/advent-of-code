package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day13Test : DayTest<Int, Int>() {
    override val puzzle = { Day13() }

    @Test
    fun day13_1() {
        runTest("_1", 405, 0)
    }
}
