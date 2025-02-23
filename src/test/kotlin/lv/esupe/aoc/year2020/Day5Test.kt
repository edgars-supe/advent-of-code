package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day5Test : DayTest<Int, Int>() {
    override val puzzle = { Day5() }

    @Test
    fun day5_1() {
        testPartOneWithFile("_1", 820)
    }

    @Test
    fun day5_2() {
        testPartOneWithFile("_2", 357)
    }
}
