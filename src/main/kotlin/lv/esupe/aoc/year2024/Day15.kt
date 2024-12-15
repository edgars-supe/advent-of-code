package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day15() }

class Day15 : Puzzle<Int, Int>(2024, 15) {
    override val input = rawInput
    private val gridLines: List<String>
    private val movements: List<Direction>

    init {
        val (gridLines, movementLines) = rawInput.chunkedBy { it.isBlank() }
        this.gridLines = gridLines
        movements = movementLines.flatMap { line ->
            line.map { char ->
                when (char) {
                    '<' -> Direction.West
                    '^' -> Direction.South
                    '>' -> Direction.East
                    'v' -> Direction.North
                    else -> error("Unknown direction")
                }
            }
        }
    }

    override fun solvePartOne(): Int {
        val grid = Grid.from(gridLines)
        moveRobot(grid, ::tryMove)
        return addCoordinates(grid, BOX)
    }

    override fun solvePartTwo(): Int {
        val grid = Grid.from(expandedMap())
        moveRobot(grid, ::tryMove2)
        return addCoordinates(grid, BOX_LEFT)
    }

    private fun moveRobot(grid: Grid<Char>, moveFunction: (Grid<Char>, Point, Direction) -> Boolean) {
        var robot = grid.entries.first { (_, char) -> char == ROBOT }.key
        movements.forEach { direction ->
            if (moveFunction(grid, robot, direction)) {
                robot = robot.move(direction)
            }
        }
    }

    private fun addCoordinates(grid: Grid<Char>, targetChar: Char) = grid.entries
        .filter { (_, char) -> char == targetChar }
        .sumOf { (point, _) -> point.x + point.y * 100 }

    private fun expandedMap() = gridLines.map { line ->
        line
            .map { char ->
                when (char) {
                    ROBOT -> "$ROBOT$FLOOR"
                    FLOOR -> "$FLOOR$FLOOR"
                    WALL -> "$WALL$WALL"
                    BOX -> "$BOX_LEFT$BOX_RIGHT"
                    else -> error("Unknown character $char")
                }
            }
            .joinToString(separator = "")
    }

    private fun tryMove(grid: Grid<Char>, point: Point, direction: Direction): Boolean {
        val np = point.move(direction)
        val nc = grid[np]

        fun move() {
            grid.remove(point)
                ?.let { grid[np] = it }
        }

        return when (nc) {
            BOX -> {
                if (tryMove(grid, np, direction)) {
                    move()
                    true
                } else {
                    false
                }
            }
            WALL -> false
            else -> {
                move()
                true
            }
        }
    }

    private fun tryMove2(grid: Grid<Char>, point: Point, direction: Direction): Boolean {
        if (!canMove(grid, point, direction)) return false

        move(grid, point, direction)
        return true
    }

    private fun canMove(grid: Grid<Char>, point: Point, direction: Direction): Boolean {
        val np = point.move(direction)
        val nc = grid[np]
        val isVertical = direction == Direction.North || direction == Direction.South

        return when (nc) {
            BOX_LEFT -> {
                if (isVertical) canMove(grid, np, direction) && canMove(grid, np.right(), direction)
                else canMove(grid, np, direction)
            }
            BOX_RIGHT -> {
                if (isVertical) canMove(grid, np, direction) && canMove(grid, np.left(), direction)
                else canMove(grid, np, direction)
            }
            WALL -> false
            else -> true
        }
    }

    private fun move(grid: Grid<Char>, point: Point, direction: Direction, moveNeighbor: Boolean = true) {
        val char = grid[point]
        val np = point.move(direction)
        val isVertical = direction == Direction.North || direction == Direction.South

        when (char) {
            BOX_LEFT -> {
                move(grid, np, direction)
                if (isVertical && moveNeighbor) {
                    move(grid, point.right(), direction, moveNeighbor = false)
                }
            }
            BOX_RIGHT -> {
                move(grid, np, direction)
                if (isVertical && moveNeighbor) {
                    move(grid, point.left(), direction, moveNeighbor = false)
                }
            }
            ROBOT -> move(grid, np, direction)
            else -> return
        }
        grid.remove(point)
            ?.let { grid[np] = it }
    }

    companion object {
        private const val ROBOT = '@'
        private const val BOX = 'O'
        private const val WALL = '#'
        private const val FLOOR = '.'
        private const val BOX_LEFT = '['
        private const val BOX_RIGHT = ']'
    }
}
