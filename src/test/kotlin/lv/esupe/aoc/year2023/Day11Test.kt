package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day11Test : DayTest<Long, Long>() {
    override val puzzle = { Day11(expansionRate = 10) }

    @Test
    fun day11_1() {
        runTest("_1", 374, 1030)
    }
}
