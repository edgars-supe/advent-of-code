package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day14Test : DayTest<Long, Long>() {
    override val puzzle = { Day14() }

    @Test
    fun day14_1() {
        runTest("_1", 1588, 2188189693529L)
    }
}
