package lv.esupe.aoc.year2025

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day2Test : DayTest<Long, Long>() {
    override val puzzle = { Day2() }

    @Test
    fun day2_1() {
        runTest("_1", 1227775554, 4174379265)
    }
}
