package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day18Test : DayTest<Int, Int>() {
    override val puzzle = { Day18() }

    @Test
    fun day18_1() {
        runTest("_1", 4140, 3993)
    }
}
