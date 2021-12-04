package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day4Test : DayTest<Int, Int>() {
    override val puzzle = { Day4() }

    @Test
    fun day4_1() {
        runTest("_1", 4512, 1924)
    }
}
