package lv.esupe.aoc.year2019

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test


class Day3Test : DayTest<Int, Int>() {

    override val puzzle = { Day3() }

    @Test
    fun day3_1() {
        runTest("_1", 6, 30)
    }

    @Test
    fun day3_2() {
        runTest("_2", 159, 610)
    }

    @Test
    fun day3_3() {
        runTest("_3", 135, 410)
    }
}