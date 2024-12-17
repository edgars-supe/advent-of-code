package lv.esupe.aoc.year2024

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Test

class Day17Test : DayTest<String, Long>() {
    override val puzzle = { Day17() }

    @Test
    fun day17_1() {
        testPartOneWithFile("_1", "4,6,3,5,6,3,5,2,1,0")
    }

    @Test
    fun day17_2() {
        val input = createInput(a = 10, program = "5,0,5,1,5,4")
        testPartOne(input, "0,1,2")
    }

    @Test
    fun day17_3() {
        val input = createInput(a = 2024, program = "0,1,5,4,3,0")
        testPartOne(input, "4,2,5,6,7,7,7,7,3,1,0")
    }

    private fun createInput(a: Int = 0, b: Int = 0, c: Int = 0, program: String): List<String> {
        return """
            Register A: $a
            Register B: $b
            Register C: $c
            
            Program: $program
        """.trimIndent().lines()
    }
}
