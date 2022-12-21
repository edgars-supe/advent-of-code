package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day21Test : DayTest<Long, Int>() {
    override val puzzle = { Day21() }

    @Test
    fun day21_1() {
        runTest("_1", 152, 0)
    }
}
