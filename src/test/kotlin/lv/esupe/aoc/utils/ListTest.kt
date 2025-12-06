package lv.esupe.aoc.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ListTest {

    @Nested
    inner class At {
        private val list = listOf(1, 2, 3)

        @Test
        fun `Positive values return elements as usual`() {
            assertEquals(1, list.at(0))
            assertEquals(2, list.at(1))
            assertEquals(3, list.at(2))
        }

        @Test
        fun `Negative values return elements in reverse`() {
            assertEquals(3, list.at(-1))
            assertEquals(2, list.at(-2))
            assertEquals(1, list.at(-3))
        }

        @Test
        fun `atOrNull returns null when index out of bounds`() {
            assertNull(list.atOrNull(3))
            assertNull(list.atOrNull(-4))
        }
    }

    @Nested
    inner class Transpose {
        @Test
        fun `transpose correctly transposes a matrix`() {
            val input = listOf(
                listOf('0', '1', '2'),
                listOf('3', '4', '5'),
                listOf('6', '7', '8'),
                listOf('9', 'a', 'b')
            )
            val expected = listOf(
                listOf('0', '3', '6', '9'),
                listOf('1', '4', '7', 'a'),
                listOf('2', '5', '8', 'b')
            )
            assertEquals(expected, input.transposed())
        }
    }
}