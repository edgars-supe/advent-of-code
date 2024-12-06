package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day6Test : DayTest<Int, Int>() {
    override val puzzle = { Day6() }

    @Test
    fun day6_1() {
        runTest("_1", 41, 6)
    }
}
