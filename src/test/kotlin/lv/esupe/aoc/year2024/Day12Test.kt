package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day12Test : DayTest<Int, Int>() {
    override val puzzle = { Day12() }

    @Test
    fun day12_1() {
        runTest("_1", 1930, 1206)
    }

    @Test
    fun day12_2() {
        runTest("_2", 140, 80)
    }

    @Test
    fun day12_3() {
        testPartTwoWithFile("_3", 236)
    }

}
