package lv.esupe.aoc.year2022

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day10Test : DayTest<Int, String>() {
    override val puzzle = { Day10() }

    @Test
    fun day10_1() {
        runTest(
            "_1",
            13140,
            """
                
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent()
        )
    }
}
