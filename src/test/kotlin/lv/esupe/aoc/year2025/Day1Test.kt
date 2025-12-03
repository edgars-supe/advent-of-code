package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day1Test : DayTest<Int, Int>() {
    override val puzzle = { Day1() }

    @Test
    fun day1_1() {
        runTest("_1", 3, 6)
    }

    @Test
    fun day1_2() {
        test(listOf("R250", "L250"), 1, 5)
    }
}
