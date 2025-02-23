package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day17Test : DayTest<Int, Long>() {
    override val puzzle = { Day17() }

    @Test
    fun day17_1() {
            runTest("_1", 3068, 0) // TODO fix part2: 1514285714288L
    }
}
