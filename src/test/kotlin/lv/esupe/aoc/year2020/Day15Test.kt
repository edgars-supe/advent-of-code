package lv.esupe.aoc.year2020

import lv.esupe.aoc.DayTest
import lv.esupe.aoc.Puzzle
import org.junit.jupiter.api.Test

class Day15Test : DayTest<Int, Int>() {
    override val puzzle = { Day15() }

    @Test
    fun day15_1() {
        runTest("_1", 436, 175594)
    }

    @Test
    fun day15_2() {
        runTest("_2", 1, 2578)
    }

    @Test
    fun day15_3() {
        runTest("_3", 10, 3544142)
    }

    @Test
    fun day15_4() {
        runTest("_4", 27, 261214)
    }

    @Test
    fun day15_5() {
        runTest("_5", 78, 6895259)
    }

    @Test
    fun day15_6() {
        runTest("_6", 438, 18)
    }


    @Test
    fun day15_7() {
        runTest("_7", 1836, 362)
    }

}
