package lv.esupe.aoc.utils

import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PathsTest {

    @Test
    fun `Verify findAllPaths finds all paths`() {
        val grid = listOf("012", "345", "678").let { Grid.from(it) }
        val paths = Paths.findAllPaths(
            start = Point(0, 0),
            target = Point(2, 2),
            getNeighbors = { p -> grid.getNeighbors(p, ignoreDefault = true, diagonal = false).map { it.first } }
        )
        assertEquals(12, paths.size)
    }

}