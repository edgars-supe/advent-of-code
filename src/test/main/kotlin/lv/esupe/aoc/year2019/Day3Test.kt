package lv.esupe.aoc.year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day3Test {

    @Test
    fun day3_1() {
        val puzzle = Day3("day3_1.in")
        val part1 = puzzle.solvePartOne()
        assertEquals(6, part1)
        val part2 = puzzle.solvePartTwo()
        assertEquals(30, part2)
    }
}