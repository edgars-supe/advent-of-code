package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day25Test : DayTest<Int, Int>() {
    override val puzzle = { Day25() }

    @Test
    fun day25_1() {
        runTest("_1", 3, 0)
    }
}
