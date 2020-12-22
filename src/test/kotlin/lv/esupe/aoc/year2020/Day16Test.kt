package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day16Test : DayTest<Int, Int>() {
    override val puzzle = { Day16() }

    @Test
    fun day16_1() {
        runTest("_1", 71, -2)
    }

}
