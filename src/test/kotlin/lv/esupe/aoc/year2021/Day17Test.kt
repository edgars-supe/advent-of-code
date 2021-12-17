package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day17Test : DayTest<Int, Int>() {
    override val puzzle = { Day17() }

    @Test
    fun day17_1() {
        test("target area: x=20..30, y=-10..-5", 45, 112)
    }
}
