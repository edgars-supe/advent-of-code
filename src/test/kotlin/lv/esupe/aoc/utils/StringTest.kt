package lv.esupe.aoc.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StringTest {
    @Nested
    inner class Transposed {
        @Test
        fun `transposed correctly transforms list of Strings`() {
            val input = listOf(
                "012",
                "345",
                "678",
                "9ab"
            )
            val expected = listOf(
                "0369",
                "147a",
                "258b"
            )
            assertEquals(expected, input.transposed())
        }
    }

    @Nested
    inner class Rotated90 {
        @Test
        fun `Rotated90 correctly rotates list of Strings clockwise`() {
            val input = listOf(
                "012",
                "345",
                "678",
                "9ab"
            )
            val expected = listOf(
                "9630",
                "a741",
                "b852"
            )
            assertEquals(expected, input.rotated90(ccw = false))
        }

        @Test
        fun `Rotated90 correctly rotates list of Strings counter-clockwise`() {
            val input = listOf(
                "012",
                "345",
                "678",
                "9ab"
            )
            val expected = listOf(
                "258b",
                "147a",
                "0369"
            )
            assertEquals(expected, input.rotated90(ccw = true))
        }
    }

    @Nested
    inner class Rotated180 {
        @Test
        fun `Rotated180 correctly rotates list of Strings`() {
            val input = listOf(
                "012",
                "345",
                "678",
                "9ab"
            )
            val expected = listOf(
                "ba9",
                "876",
                "543",
                "210"
            )
            assertEquals(expected, input.rotated180())
        }
    }
}