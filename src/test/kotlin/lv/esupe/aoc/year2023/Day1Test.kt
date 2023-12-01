package lv.esupe.aoc.year2023

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day1Test : DayTest<Int, Int>() {
    override val puzzle = { Day1() }

    @Test
    fun day1_1() {
        testPartOne(
            listOf(
                "1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet"
            ),
            142
        )
    }

    @Test
    fun day1_2() {
        testPartTwo(
            listOf(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen",
            ),
            281,
            runPartOne = false
        )
    }
}
