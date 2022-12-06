package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day6Test : DayTest<Int, Int>() {
    override val puzzle = { Day6() }

    @Test
    fun day6_1() {
        test("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19)
        test("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23)
        test("nppdvjthqldpwncqszvftbrmjlhg", 6, 23)
        test("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29)
        test("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
    }
}
