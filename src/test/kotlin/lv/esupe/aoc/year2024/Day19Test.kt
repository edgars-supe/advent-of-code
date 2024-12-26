package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day19Test : DayTest<Int, Long>() {
    override val puzzle = { Day19() }

    @Test
    fun day19_1() {
        runTest("_1", 6, 16)
    }
}
