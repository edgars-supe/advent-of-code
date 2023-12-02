package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2023, 2) {
    override val input = rawInput
    private val games = input.map { line -> Game.fromString(line) }

    override fun solvePartOne(): Int {
        return games
            .filter { game -> game.maxRed <= 12 && game.maxGreen <= 13 && game.maxBlue <= 14 }
            .sumOf { it.id }
    }

    override fun solvePartTwo(): Int {
        return games.sumOf { game -> game.maxRed * game.maxGreen * game.maxBlue }
    }

    data class Game(val id: Int, val sets: List<List<Pair<Int, String>>>) {
        private val flattenedSets = sets.flatten()

        val maxRed by lazy { maxCubesByColor("red") }
        val maxGreen by lazy { maxCubesByColor("green") }
        val maxBlue by lazy { maxCubesByColor("blue") }

        private fun maxCubesByColor(target: String): Int {
            return flattenedSets
                .filter { (_, color) -> color == target }
                .maxOfOrNull { (count, _) -> count }
                ?: 0
        }

        companion object {
            fun fromString(string: String): Game {
                val (gameIdPart, setsPart) = string.split(":")
                val gameId = gameIdPart.split(" ").last().toInt()

                val sets = setsPart
                    .split(";")
                    .map { line ->
                        line.split(",")
                            .map { cubes ->
                                val (count, color) = cubes.trim().split(" ")
                                count.toInt() to color
                            }
                    }
                return Game(gameId, sets)
            }
        }
    }
}
