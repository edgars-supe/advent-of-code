package lv.esupe.aoc.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RangeTest {

    @Nested
    inner class Overlaps {

        @Test
        fun `overlaps functions correctly`() {
            assertTrue(1..10 overlaps 5..15)
            assertTrue(1..10 overlaps 5..10)
            assertTrue(1..10 overlaps 10..20)
            assertTrue(1..10 overlaps 1..10)
            assertTrue(1..10 overlaps (1 until 10))
            assertTrue(1..10 overlaps (2 until 10))
            assertTrue(1..10 overlaps -10..1)
            assertTrue(1..10 overlaps -10..5)

            assertFalse(1..10 overlaps -10..0)
            assertFalse(1..10 overlaps (-10 until 1))
            assertFalse(1..10 overlaps 11..20)
        }
    }

    @Nested
    inner class MergeWith {

        @Test
        fun `mergeWith functions correctly`() {
            assertEquals(1..10, (1..5).mergeWith(5..10))
            assertEquals(1..10, (1..10).mergeWith(2..5))
            assertEquals(1..10, (1..7).mergeWith(4..10))
            assertEquals(1..10, (9..10).mergeWith(1..9))
        }
    }
}