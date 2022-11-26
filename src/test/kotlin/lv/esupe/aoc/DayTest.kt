package lv.esupe.aoc

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals


abstract class DayTest<T, R> {
    abstract val puzzle: () -> Puzzle<T, R>

    @AfterEach
    fun tearDown() {
        Solver.suffix = ""
        Solver.inputProvider = Solver.defaultInputProvider
    }

    /**
     * Tests the solution using the input file at `src/test/resources/input/year$year/day$day_$suffix.in`
     */
    fun runTest(suffix: String, part1: T, part2: R) {
        Solver.suffix = suffix
        Solver.inputProvider = Solver.defaultInputProvider
        val p = puzzle()
        assertEquals(part1, p.solvePartOne(), "Part 1, incorrect result")
        assertEquals(part2, p.solvePartTwo(), "Part 2, incorrect result")
    }

    /**
     * Tests the solution using `input` as the puzzle input.
     */
    fun test(input: String, part1: T, part2: R) {
        test(listOf(input), part1, part2)
    }

    /**
     * Tests the solution using `input` as the lines of the input file.
     */
    fun test(input: List<String>, part1: T, part2: R) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        assertEquals(part1, p.solvePartOne(), "Part 1, incorrect result")
        assertEquals(part2, p.solvePartTwo(), "Part 2, incorrect result")
    }

    /**
     * Tests the solution for the first part using `input` as the puzzle input.
     */
    fun testPartOne(input: String, expectedResult: T) {
        testPartOne(listOf(input), expectedResult)
    }

    /**
     * Tests the solution for the first part using `input` as the lines of the input file.
     */
    fun testPartOne(input: List<String>, expectedResult: T) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        assertEquals(expectedResult, p.solvePartOne(), "Part 1, incorrect result")
    }

    /**
     * Tests the solution for the second part using `input` as the puzzle input. Runs the first part beforehand to
     * ensure the second part can use the computations from the first part if necessary.
     */
    fun testPartTwo(input: String, expectedResult: R) {
        testPartTwo(listOf(input), expectedResult)
    }

    /**
     * Tests the solution for the second part using `input` as the lines of the input file. Runs the first part
     * beforehand to ensure the second part can use the computations from the first part if necessary.
     */
    fun testPartTwo(input: List<String>, expectedResult: R) {
        Solver.inputProvider = { _, _ -> input }
        val p = puzzle()
        p.solvePartOne()
        assertEquals(expectedResult, p.solvePartTwo(), "Part 1, incorrect result")
    }
}
