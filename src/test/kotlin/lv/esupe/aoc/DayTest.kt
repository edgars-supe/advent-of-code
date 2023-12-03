package lv.esupe.aoc

import lv.esupe.aoc.solver.DefaultInputProvider
import lv.esupe.aoc.solver.InputProvider
import lv.esupe.aoc.solver.StringInputProvider
import lv.esupe.aoc.solver.TestFileInputProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals


abstract class DayTest<T, R> {
    abstract val puzzle: () -> Puzzle<T, R>

    @AfterEach
    fun tearDown() {
        InputProvider.installedInputProvider = DefaultInputProvider()
    }

    /**
     * Tests the solution using the input file at `src/test/resources/input/year$year/day$day_$suffix.in`
     */
    fun runTest(suffix: String, part1: T, part2: R) {
        InputProvider.installedInputProvider = TestFileInputProvider(suffix)
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
        InputProvider.installedInputProvider = StringInputProvider(input)
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
        InputProvider.installedInputProvider = StringInputProvider(input)
        val p = puzzle()
        assertEquals(expectedResult, p.solvePartOne(), "Part 1, incorrect result")
    }

    /**
     * Tests the solution for the second part using `input` as the puzzle input.
     *
     * @param runPartOne If `true`, runs the first part beforehand to ensure the second part can use the computations
     *  from the first part if necessary.
     */
    fun testPartTwo(input: String, expectedResult: R, runPartOne: Boolean = true) {
        testPartTwo(listOf(input), expectedResult, runPartOne)
    }

    /**
     * Tests the solution for the second part using `input` as the lines of the input file.
     *
     * @param runPartOne If `true`, runs the first part beforehand to ensure the second part can use the computations
     *  from the first part if necessary.
     */
    fun testPartTwo(input: List<String>, expectedResult: R, runPartOne: Boolean = true) {
        InputProvider.installedInputProvider = StringInputProvider(input)
        val p = puzzle()
        if (runPartOne) p.solvePartOne()
        assertEquals(expectedResult, p.solvePartTwo(), "Part 2, incorrect result")
    }
}
