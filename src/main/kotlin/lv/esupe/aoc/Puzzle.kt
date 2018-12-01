package lv.esupe.aoc


abstract class Puzzle<T>(year: Int, day: Int, puzzle: Int) {
    val input = getInput(year, day, puzzle)

    abstract fun calculate(): T
}