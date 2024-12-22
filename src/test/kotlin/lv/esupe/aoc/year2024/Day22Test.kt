package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day22Test : DayTest<Long, Long>() {
    override val puzzle = { Day22() }

    @Test
    fun day22_1() {
        runTest("_1", 37327623, 0)
    }
}
