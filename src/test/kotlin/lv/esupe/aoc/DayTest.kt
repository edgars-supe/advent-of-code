package lv.esupe.aoc

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals


abstract class DayTest {
    abstract val puzzle: () -> Puzzle<*, *>

    @AfterEach
    fun tearDown() {
        Solver.suffix = ""
    }

    fun runTest(suffix: String, part1: Int, part2: Int) {
        Solver.suffix = suffix
        val p = puzzle()
        assertEquals(part1, p.solvePartOne(), "Part 1, incorrect result")
        assertEquals(part2, p.solvePartTwo(), "Part 2, incorrect result")
    }

}