package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day16Test : DayTest<Int, Int>() {
    override val puzzle = { Day16() }

    @Test
    fun day16_1() {
        runTest("_1", 7036, 45)
    }

    @Test
    fun day16_2() {
        runTest("_2", 11048, 64)
    }
}
