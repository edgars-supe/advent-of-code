package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day7Test : DayTest<Int, Int>() {
    override val puzzle = { Day7() }

    @Test
    fun day7_1() {
        runTest("_1", 37, 168)
    }
}
