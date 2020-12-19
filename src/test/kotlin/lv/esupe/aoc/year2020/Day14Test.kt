package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day14Test : DayTest<Long, Long>() {
    override val puzzle = { Day14() }

    @Test
    fun day14_1() {
        runTest("_1", 165, 208)
    }
}
