package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, Int>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest("_1", 4, 1)
    }

    @Test
    fun day10_2() {
        runTest("_2", 8, 1)
    }

    @Test
    fun day10_3() {
        testPartTwoWithFile("_3", 4, runPartOne = false)
    }

    @Test
    fun day10_4() {
        testPartTwoWithFile("_4", 8, runPartOne = false)
    }

    @Test
    fun day10_5() {
        testPartTwoWithFile("_5", 10, runPartOne = false)
    }
}
