package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day13() }

class Day13 : Puzzle<String, Int>(2018, 13) {
    override val input = rawInput
    //private val track = mutableMapOf<Point, Track>()
    private val carts = mutableListOf<Cart>()

    override fun solvePartOne(): String {
        return "a"
    }

    override fun solvePartTwo(): Int {
        return 0
    }
}

/*sealed class Track(val location: Point, val directions: Set<Direction>) {
    class Junction(location: Point) : Track(location, Direction.values().toSet()) {
        override fun moveCart(cart: Cart): Cart {

        }

        private val Cart.nextJunctionDirection: Direction
            get() {
                when (lastJunctionDirection) {
                    Direction.North -> Direction.East
                    Direction.West -> Direction.North
                    Direction.East -> Direction.West
                    Direction.South -> Direction.West
                }
            }
    }

    class Rail(location: Point, directions: Set<Direction>) : Track(location, directions) {

    }

    abstract fun moveCart(cart: Cart): Cart
}*/

data class Cart(
    val location: Point,
    val direction: Direction,
    val lastJunctionDirection: Direction = Direction.East
) {
    fun move(direction: Direction) = Cart(location.move(direction), direction)

    fun turn(direction: Direction) = copy(direction = direction)
}
