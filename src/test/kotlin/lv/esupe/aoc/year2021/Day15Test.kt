package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day15Test : DayTest<Int, Int>() {
    override val puzzle = { Day15() }

    @Test
    fun day15_1() {
        runTest("_1", 40, 315)
    }
}
