package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day3Test : DayTest<Int, Int>() {
    override val puzzle = { Day3() }

    @Test
    fun day3_1() {
        runTest("_1", 157, 70)
    }
}
