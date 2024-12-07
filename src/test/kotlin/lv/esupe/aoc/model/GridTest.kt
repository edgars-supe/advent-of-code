package lv.esupe.aoc.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GridTest {

    @Test
    fun `It parses grid correctly`() {
        val input = createInput(
            "12345",
            "67890",
            "abcde",
            "fghij"
        )
        val subject = Grid.from(input)
        assertEquals(5, subject.width)
        assertEquals(4, subject.height)
        assertEquals('1', subject[0, 0])
        assertEquals('5', subject[4, 0])
        assertEquals('6', subject[0, 1])
        assertEquals('0', subject[4, 1])
        assertEquals('a', subject[0, 2])
        assertEquals('e', subject[4, 2])
        assertEquals('f', subject[0, 3])
        assertEquals('j', subject[4, 3])
        assertEquals(20, subject.size)
    }

    @Test
    fun `It parses grid correctly with empty lines`() {
        val input = createInput(
            ".....",
            "12345",
            "67890",
            "abcde",
            "fghij",
            "....."
        )
        val subject = Grid.from(input, default = '.')
        assertEquals(5, subject.width)
        assertEquals(6, subject.height)
        assertEquals('1', subject[0, 1])
        assertEquals('5', subject[4, 1])
        assertEquals('6', subject[0, 2])
        assertEquals('0', subject[4, 2])
        assertEquals('a', subject[0, 3])
        assertEquals('e', subject[4, 3])
        assertEquals('f', subject[0, 4])
        assertEquals('j', subject[4, 4])
        assertEquals(30, subject.size)
    }

    @Test
    fun `It parses grid correctly with empty lines and skipping default`() {
        val input = createInput(
            ".....",
            "12345",
            "67890",
            "abcde",
            "fghij",
            "....."
        )
        val subject = Grid.from(input, default = '.', insertDefault = false)
        assertEquals(5, subject.width)
        assertEquals(4, subject.height)
        assertEquals('1', subject[0, 1])
        assertEquals('5', subject[4, 1])
        assertEquals('6', subject[0, 2])
        assertEquals('0', subject[4, 2])
        assertEquals('a', subject[0, 3])
        assertEquals('e', subject[4, 3])
        assertEquals('f', subject[0, 4])
        assertEquals('j', subject[4, 4])
        assertEquals(20, subject.size)
    }

    @Test
    fun `It parses grid correctly with invert y`() {
        val input = createInput(
            "12345",
            "67890",
            "abcde",
            "fghij",
        )
        val subject = Grid.from(input, default = '.', invertY = true)
        assertEquals(5, subject.width)
        assertEquals(4, subject.height)
        assertEquals('1', subject[0, 3])
        assertEquals('5', subject[4, 3])
        assertEquals('6', subject[0, 2])
        assertEquals('0', subject[4, 2])
        assertEquals('a', subject[0, 1])
        assertEquals('e', subject[4, 1])
        assertEquals('f', subject[0, 0])
        assertEquals('j', subject[4, 0])
    }


    @Test
    fun `Size is recalculated when inserting`() {
        val input = createInput(
            "12345",
            "67890",
            "abcde",
            "fghij",
        )
        val subject = Grid.from(input)
        subject[-1, -1] = 'X'
        assertEquals('X', subject[-1, -1])
        assertEquals(5, subject.height)
        assertEquals(6, subject.width)
        val expectedPrintout = listOf(
            "X.....",
            ".12345",
            ".67890",
            ".abcde",
            ".fghij"
        )
            .joinToString(separator = "\n")
        assertEquals(expectedPrintout, subject.toString())
    }

    private fun createInput(vararg lines: String): List<String> {
        return lines.toList()
    }
}