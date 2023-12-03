package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2023, 3) {
    override val input = rawInput
    private val height: Int = rawInput.size
    private val width: Int = rawInput[0].length
    private val symbols: Set<Symbol>
    private val numbers: List<Number>

    init {
        numbers = parseNumbers()
        symbols = numbers.flatMap { it.symbols }.toSet()
    }

    override fun solvePartOne(): Int {
        return numbers
            .filter { number -> number.symbols.isNotEmpty() }
            .sumOf { it.number }
    }

    override fun solvePartTwo(): Int {
        return symbols.filter { s -> s.symbol == '*' } // gear symbols
            .associateWith { gs -> numbers.filter { n -> gs in n.symbols } } // map to gear symbols -> [numbers]
            .filter { (_, numbers) -> numbers.size == 2 }
            .map { (_, numbers) -> numbers.fold(1) { acc, n -> acc * n.number } }
            .sum()
    }

    private fun parseNumbers(): MutableList<Number> {
        val numbers = mutableListOf<Number>()
        (0 until height).forEach { y ->
            var skipX = -1
            (0 until width).forEach x@{ x ->
                if (x <= skipX) return@x

                val char = rawInput[y][x]
                if (char.isDigit()) {
                    val (number, newSkipX) = parseNumber(x, y)
                    numbers += number
                    skipX = x + newSkipX
                }
            }
        }
        return numbers
    }

    /**
     * Parses the number on the `y` line, starting at the `x`th character. Returns the number with its adjacent Symbols
     * and the length of the number in characters.
     */
    private fun parseNumber(x: Int, y: Int): Pair<Number, Int> {
        var number = 0
        var currX = x
        var char = input[y][currX]
        while (char.isDigit()) {
            number = number * 10 + char.digitToInt()
            char = input[y].getOrNull(++currX) ?: break
        }
        val minX = x - 1
        val maxX = currX
        val symbols = findAdjacentSymbols(minX, maxX, y)
        return Number(number, symbols) to (maxX - minX - 2)
    }

    private fun findAdjacentSymbols(minX: Int, maxX: Int, y: Int): Set<Symbol> {
        val symbols = mutableSetOf<Symbol>()
        if (y - 1 >= 0) { // find symbols in row above
            (minX..maxX).forEach { x ->
                val c = rawInput[y - 1].getOrNull(x)
                if (c?.isSymbol() == true) symbols += Symbol(c, Point(x, y - 1))
            }
        }
        if (minX >= 0) { // find symbol to the left of first digit
            val toLeft = rawInput[y][minX]
            if (toLeft.isSymbol()) symbols += Symbol(toLeft, Point(minX, y))
        }
        if (maxX < width) { // find symbol to the right of last digit
            val toRight = rawInput[y][maxX]
            if (toRight.isSymbol()) symbols += Symbol(toRight, Point(maxX, y))
        }
        if (y + 1 < height) { // find symbols in row below
            (minX..maxX).forEach { x ->
                val c = rawInput[y + 1].getOrNull(x)
                if (c?.isSymbol() == true) symbols += Symbol(c, Point(x, y + 1))
            }
        }
        return symbols
    }

    private fun Char.isSymbol() = !isDigit() && this != '.'

    data class Number(val number: Int, val symbols: Set<Symbol>)

    data class Symbol(val symbol: Char, val point: Point)
}
