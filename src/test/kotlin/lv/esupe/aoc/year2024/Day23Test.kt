package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day23Test : DayTest<Int, Int>() {
    override val puzzle = { Day23() }

    @Test
    fun day23_1() {
        runTest("_1", 7, 0)
    }
}
