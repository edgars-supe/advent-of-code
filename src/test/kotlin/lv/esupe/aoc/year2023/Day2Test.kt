package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day2Test : DayTest<Int, Int>() {
    override val puzzle = { Day2() }

    @Test
    fun day2_1() {
        runTest("_1", 8, 2286)
    }
}
