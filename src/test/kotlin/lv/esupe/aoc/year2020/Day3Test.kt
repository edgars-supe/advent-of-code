package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day3Test : DayTest<Int, Int>() {
    override val puzzle = { Day3() }

    @Test
    fun day3_1() {
        runTest("_1", 7, 336)
    }
}
