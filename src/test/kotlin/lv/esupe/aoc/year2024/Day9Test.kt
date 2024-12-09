package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day9Test : DayTest<Long, Long>() {
    override val puzzle = { Day9() }

    @Test
    fun day9_1() {
        runTest("_1", 1928, 2858)
    }
}
