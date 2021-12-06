package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day6Test : DayTest<Long, Long>() {
    override val puzzle = { Day6() }

    @Test
    fun day6_1() {
        runTest("_1", 5934L, 26984457539L)
    }
}
