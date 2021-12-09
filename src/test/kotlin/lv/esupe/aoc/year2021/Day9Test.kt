package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day9Test : DayTest<Int, Int>() {
    override val puzzle = { Day9() }

    @Test
    fun day9_1() {
        runTest("_1", 15, 1134)
    }
}
