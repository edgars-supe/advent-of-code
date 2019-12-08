package lv.esupe.aoc.year2019

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day1Test : DayTest() {
    override val puzzle = { Day1() }

    @Test
    fun day1_1() {
        runTest("_1", 2, 2)
    }

    @Test
    fun day1_2() {
        runTest("_2", 2, 2)
    }

    @Test
    fun day1_3() {
        runTest("_3", 654, 966)
    }

    @Test
    fun day1_4() {
        runTest("_4", 33583, 50346)
    }
}