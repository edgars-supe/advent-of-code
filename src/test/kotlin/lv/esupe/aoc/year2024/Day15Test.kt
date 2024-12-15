package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day15Test : DayTest<Int, Int>() {
    override val puzzle = { Day15() }

    @Test
    fun day15_1() {
        runTest("_1", 10092, 9021)
    }

    @Test
    fun day15_2() {
        testPartOneWithFile("_2", 2028)
    }
}
