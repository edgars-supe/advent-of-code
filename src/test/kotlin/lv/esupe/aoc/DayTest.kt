package lv.esupe.aoc

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals


abstract class DayTest<T, R> {
    abstract val puzzle: () -> Puzzle<T, R>

    @AfterEach
    fun tearDown() {
        Solver.suffix = ""
    }

    fun runTest(suffix: String, part1: T, part2: R) {
        Solver.suffix = suffix
        val p = puzzle()
        assertEquals(part1, p.solvePartOne(), "Part 1, incorrect result")
        assertEquals(part2, p.solvePartTwo(), "Part 2, incorrect result")
    }

    fun test(input: String, part1: T, part2: R) {
        test(listOf(input), part1, part2)
    }

    fun test(input: List<String>, part1: T, part2: R) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        assertEquals(part1, p.solvePartOne(), "Part 1, incorrect result")
        assertEquals(part2, p.solvePartTwo(), "Part 2, incorrect result")
    }


    fun testPartOne(input: String, expectedResult: T) {
        testPartOne(listOf(input), expectedResult)
    }

    fun testPartOne(input: List<String>, expectedResult: T) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        assertEquals(expectedResult, p.solvePartOne(), "Part 1, incorrect result")
    }

    fun testPartTwo(input: String, expectedResult: R) {
        testPartTwo(listOf(input), expectedResult)
    }

    fun testPartTwo(input: List<String>, expectedResult: R) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        p.solvePartOne()
        assertEquals(expectedResult, p.solvePartTwo(), "Part 1, incorrect result")
    }
}
