package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day20Test : DayTest<Int, Int>() {
    override val puzzle = { Day20(picosecondsToSavePart1 = 1, picosecondsToSavePart2 = 50) }

    @Test
    fun day20_1() {
        runTest("_1", 44, 285)
    }
}
