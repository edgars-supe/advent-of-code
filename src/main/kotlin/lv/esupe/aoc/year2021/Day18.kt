package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.forAllUniquePairs
import kotlin.math.ceil
import kotlin.math.floor

fun main() = solve { Day18() }

class Day18 : Puzzle<Int, Int>(2021, 18) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        val numbers = rawInput.map { line -> parseLine(line) }
        val result = numbers.reduce { acc, sfn ->
            val root = acc + sfn
            while (root.toList().reduce()) { }
            root
        }
        return result.magnitude()
    }

    override fun solvePartTwo(): Int {
        val magnitudes = mutableListOf<Int>()
        rawInput.forAllUniquePairs { line1, line2 ->
            val a = parseLine(line1)
            val b = parseLine(line2)
            val root = a + b
            while (root.toList().reduce()) { }
            magnitudes += root.magnitude()
        }
        return magnitudes.maxOf { it }
    }

    private fun Sfn.toList(list: MutableList<Sfn> = mutableListOf()): MutableList<Sfn> {
        list.add(this)
        if (this is Sfn.Pair) {
            this.left.toList(list)
            this.right.toList(list)
        }
        return list
    }

    private fun MutableList<Sfn>.reduce(): Boolean {
        val exploder = firstOrNull { it is Sfn.Pair && it.canExplode() } as? Sfn.Pair
        if (exploder != null) {
            val index = indexOf(exploder)
            val leftNumber = take(index).lastOrNull { it is Sfn.Number } as? Sfn.Number
            val rightNumber = drop(index + 3)
                .firstOrNull { it is Sfn.Number } as? Sfn.Number
            exploder.explode(leftNumber, rightNumber)
            return true
        }
        val splitter = firstOrNull { it is Sfn.Number && it.canSplit() } as? Sfn.Number
        if (splitter != null) {
            splitter.split()
            return true
        }
        return false
    }

    sealed class Sfn {
        operator fun plus(other: Sfn): Pair = Pair(this, other)

        abstract fun magnitude(): Int

        var parent: Pair? = null

        class Number(var value: Int) : Sfn() {
            override fun magnitude(): Int {
                return value
            }

            fun canSplit(): Boolean = value >= 10

            fun split() {
                val div = value / 2.toDouble()
                val newValue = Pair(
                    left = Number(floor(div).toInt()),
                    right = Number(ceil(div).toInt())
                )
                parent?.update(this, newValue)
            }

            override fun toString(): String {
                return value.toString()
            }
        }

        class Pair(var left: Sfn, var right: Sfn) : Sfn() {
            init {
                left.parent = this
                right.parent = this
            }

            override fun magnitude(): Int {
                return 3 * left.magnitude() + 2 * right.magnitude()
            }

            fun canExplode(): Boolean {
                return left is Number && right is Number && parent?.parent?.parent?.parent != null
            }

            fun explode(leftNumber: Number?, rightNumber: Number?) {
                val myLeft = left as? Number ?: return
                val myRight = right as? Number ?: return
                leftNumber?.let { leftNumber.value += myLeft.value }
                rightNumber?.let { rightNumber.value += myRight.value }
                parent?.update(this, Number(0))
            }

            fun update(child: Sfn, newValue: Sfn) {
                if (left == child) {
                    left = newValue
                } else if (right == child) {
                    right = newValue
                }
                newValue.parent = this
            }

            override fun toString(): String {
                return "[$left,$right]"
            }
        }
    }

    private fun parseLine(input: String): Sfn.Pair {
        var index = 0

        fun parse(): Sfn.Pair {
            // index is at '['
            val first: Sfn = if (input[++index].isDigit()) {
                Sfn.Number(input[index++].digitToInt())
            } else { // if not digit, then pair
                parse()
            }
            // index is at ','
            val second: Sfn = if (input[++index].isDigit()) {
                Sfn.Number(input[index++].digitToInt())
            } else { // if not digit, then pair
                parse()
            }
            index++ // skip ']'
            return Sfn.Pair(first, second)
        }

        return parse()
    }
}
