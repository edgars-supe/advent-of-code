package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day13Test : DayTest<Long, Long>() {
    override val puzzle = { Day13() }

    @Test
    fun day13_1() {
        runTest("_1", 480, 0)
    }
}
