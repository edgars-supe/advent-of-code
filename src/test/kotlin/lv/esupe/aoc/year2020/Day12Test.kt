package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day12Test : DayTest<Int, Int>() {
    override val puzzle = { Day12() }

    @Test
    fun day12_1() {
        runTest("_1", 25, 286)
    }
}
