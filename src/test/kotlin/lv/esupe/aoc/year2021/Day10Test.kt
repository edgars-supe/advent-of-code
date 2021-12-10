package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, Long>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest("_1", 26397, 288957)
    }
}
