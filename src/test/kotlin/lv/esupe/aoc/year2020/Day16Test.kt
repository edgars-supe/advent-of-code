package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day16Test : DayTest<Int, Long>() {
    override val puzzle = { Day16() }

    @Test
    fun day16_1() {
        testPartOneWithFile("_1", 71)
    }

}
