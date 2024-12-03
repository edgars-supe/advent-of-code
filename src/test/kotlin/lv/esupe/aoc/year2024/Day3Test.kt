package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day3Test : DayTest<Int, Int>() {
    override val puzzle = { Day3() }

    @Test
    fun day3_1() {
        testPartOneWithFile("_1", 161)
    }

    @Test
    fun day3_2() {
        testPartTwoWithFile("_2", 48)
    }
}
