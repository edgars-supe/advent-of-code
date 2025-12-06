package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day6Test : DayTest<Long, Long>() {
    override val puzzle = { Day6() }

    @Test
    fun day6_1() {
        runTest("_1", 4277556, 3263827)
    }
}
