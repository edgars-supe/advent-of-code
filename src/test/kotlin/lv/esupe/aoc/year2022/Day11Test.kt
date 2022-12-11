package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day11Test : DayTest<Long, Long>() {
    override val puzzle = { Day11() }

    @Test
    fun day11_1() {
        runTest("_1", 10605, 2713310158)
    }
}
