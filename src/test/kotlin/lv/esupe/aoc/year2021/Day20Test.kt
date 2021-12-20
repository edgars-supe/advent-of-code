package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day20Test : DayTest<Int, Int>() {
    override val puzzle = { Day20() }

    @Test
    fun day20_1() {
        runTest("_1", 35, 3351)
    }
}
