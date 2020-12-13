package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day13Test : DayTest<Int, Long>() {
    override val puzzle = { Day13() }

    @Test
    fun day13_1() {
        runTest("_1", 295, 1068781)
    }

    @Test
    fun day13_2() {
        runTest("_2", 13, 3417)
    }

    @Test
    fun day13_3() {
        runTest("_3", 7, 754018)
    }

    @Test
    fun day13_4() {
        runTest("_4", 7, 779210)
    }

    @Test
    fun day13_5() {
        runTest("_5", 7, 1261476)
    }

    @Test
    fun day13_6() {
        runTest("_6", 37, 1202161486)
    }
}
