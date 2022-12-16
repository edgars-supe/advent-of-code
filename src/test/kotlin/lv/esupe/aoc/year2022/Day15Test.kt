package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day15Test : DayTest<Int, Long>() {
    override val puzzle = { Day15(findAtY = 10, xyMax = 20) }

    @Test
    fun day15_1() {
        runTest("_1", 26, 56000011)
    }
}
