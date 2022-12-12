package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day12Test : DayTest<Int, Int>() {
    override val puzzle = { Day12() }

    @Test
    fun day12_1() {
        runTest("_1", 31, 29)
    }

    @Test
    fun `It follows sequentially`() {
        testPartOne(
            listOf(
                "Sabcdghijklzzzzstuvwxy",
                "zzzzefzzzzmnopqrzzzzzE"
            ),
            26
        )
    }

    @Test
    fun `It can make jumps down, but not up`() {
        testPartOne(
            listOf(
                "SabcdefghijklabcdefghijklmnopqrstuvwxyzE"
            ),
            39
        )
    }
}
