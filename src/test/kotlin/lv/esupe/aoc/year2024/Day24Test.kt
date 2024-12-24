package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day24Test : DayTest<Long, Long>() {
    override val puzzle = { Day24() }

    @Test
    fun day24_1() {
        runTest("_1", 4, 0)
    }

    @Test
    fun day24_2() {
        runTest("_2", 2024, 0)
    }
}
