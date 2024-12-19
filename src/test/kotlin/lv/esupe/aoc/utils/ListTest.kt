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
}