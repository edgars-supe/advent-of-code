package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day8Test : DayTest<Long, Long>() {
    override val puzzle = { Day8() }

    @Test
    fun day8_1() {
        testPartOneWithFile("_1", 2)
    }

    @Test
    fun day8_2() {
        testPartOneWithFile("_2", 6)
    }

    @Test
    fun day8_3() {
        testPartTwoWithFile("_3", 6, runPartOne = false)
    }
}
