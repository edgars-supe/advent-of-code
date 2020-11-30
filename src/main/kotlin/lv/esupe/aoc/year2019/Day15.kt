package lv.esupe.aoc.year2019

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

fun main() = solve { Day15() }

class Day15 : Puzzle<Int, Int>(2019, 15) {
    companion object {
        private const val STATUS_WALL = 0L
        private const val STATUS_OK = 1L
        private const val STATUS_OXYGEN = 2L

        private const val MOVEMENT_NORTH = 1L
        private const val MOVEMENT_SOUTH = 2L
        private const val MOVEMENT_WEST = 3L
        private const val MOVEMENT_EAST = 4L
    }

    override val input = rawInput[0].toProgram()

    private val maze = mutableMapOf<Point, Long>().apply { put(Point(0, 0), STATUS_OK) }

    override fun solvePartOne(): Int {
        val intcode = Intcode(input)
        runBlocking {
            val job = launch { intcode.execute() }
            fillMaze(intcode, Point(0, 0))
            job.cancel()
        }
        return findPathLength(Point(0, 0), maze.getOxygenLocation())
    }

    override fun solvePartTwo(): Int {
        return findPathLength(maze.getOxygenLocation(), null)
    }

    private suspend fun fillMaze(intcode: Intcode, from: Point) {
        from.neighbors()
            .filter { it !in maze.keys }
            .forEach { point ->
                val direction = from.movementTo(point)
                intcode.input.send(direction)
                when (val result = intcode.output.receive()) {
                    STATUS_WALL -> maze[adjust(from, direction)] = STATUS_WALL
                    STATUS_OK, STATUS_OXYGEN -> {
                        maze[point] = result
                        fillMaze(intcode, point)
                        intcode.input.send(point.movementTo(from))
                        intcode.output.receive() // we came from here, so ignore
                    }
                }
            }
    }

    private fun findPathLength(from: Point, to: Point?): Int {
        val visited = mutableSetOf(from)
        val queue = mutableListOf(from)
        val distances = mutableMapOf(from to 0)
        while (queue.isNotEmpty()) {
            val point = queue.removeAt(0)
            val distance = distances[point] ?: -1
            if (point == to) {
                return distances[to] ?: -1
            } else {
                point.neighbors()
                    .filter { it !in visited && maze[it] != STATUS_WALL }
                    .onEach { neighbor ->
                        visited.add(neighbor)
                        distances[neighbor] = distance + 1
                    }
                    .let { queue.addAll(it) }
            }
        }
        return distances.maxOfOrNull { (_, value) -> value } ?: -1
    }

    private fun adjust(location: Point, direction: Long) =
        when (direction) {
            MOVEMENT_NORTH -> location.up()
            MOVEMENT_SOUTH -> location.down()
            MOVEMENT_WEST -> location.left()
            MOVEMENT_EAST -> location.right()
            else -> location
        }

    private fun Map<Point, Long>.getOxygenLocation() = entries
        .first { (_, value) -> value == STATUS_OXYGEN }
        .key

    private fun Point.movementTo(other: Point) = when (other) {
        up() -> MOVEMENT_NORTH
        right() -> MOVEMENT_EAST
        down() -> MOVEMENT_SOUTH
        left() -> MOVEMENT_WEST
        else -> throw IllegalArgumentException("No relation")
    }
}
