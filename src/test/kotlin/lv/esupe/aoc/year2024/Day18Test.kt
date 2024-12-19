package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day18Test : DayTest<Int, String>() {
    override val puzzle = { Day18(endX = 6, endY = 6, simulateBits = 12) }

    @Test
    fun day18_1() {
        runTest("_1", 22, "6,1")
    }
}
