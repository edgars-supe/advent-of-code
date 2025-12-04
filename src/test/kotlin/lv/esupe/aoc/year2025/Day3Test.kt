package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day3Test : DayTest<Long, Long>() {
    override val puzzle = { Day3() }

    @Test
    fun day3_1() {
        testPartTwoWithFile("_1", 3121910778619L)
        //runTest("_1", 357, 3121910778619L)
    }
}
