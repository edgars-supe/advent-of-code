package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.bold

fun main() = solve(benchmark = false) { Day4() }

class Day4 : Puzzle<Int, Int>(2021, 4) {
    override val input = rawInput
    private val drawing: List<Int> = input[0].split(",").map { it.toInt() }
    private val boards: List<Board> = input.drop(1)
        .filter { it.isNotBlank() }
        .chunked(5)
        .map { rows ->
            val board = rows.map { row ->
                row.trim()
                    .split(Regex("\\s+"))
                    .map { it.toInt() }
                    .toIntArray()
            }
            Board(board.toTypedArray())
        }

    override fun solvePartOne(): Int {
        for (number in drawing) {
            val winner = boards.firstOrNull { board -> board.mark(number) }
            if (winner != null) {
                return winner.unmarkedSum() * number
            }
        }
        error("No winner")
    }

    override fun solvePartTwo(): Int {
        val (board, number) = findLastWinner(boards, 0)
        return board.unmarkedSum() * number
    }

    private tailrec fun findLastWinner(boards: List<Board>, numberIdx: Int): Pair<Board, Int> {
        val number = drawing[numberIdx]
        return if (boards.size == 1) {
            val board = boards.first()
            if (board.mark(number)) {
                board to number
            } else {
                findLastWinner(boards, numberIdx + 1)
            }
        } else {
            findLastWinner(boards.filterNot { board -> board.mark(number) }, numberIdx + 1)
        }
    }

    class Board(val board: Array<IntArray>) {
        val marks: Array<BooleanArray> = Array(5) { BooleanArray(5) { false } }

        fun mark(number: Int): Boolean {
            outer@ for (rowIdx in board.indices) {
                for (colIdx in board[rowIdx].indices) {
                    if (board[rowIdx][colIdx] == number) {
                        marks[rowIdx][colIdx] = true
                        break@outer
                    }
                }
            }
            return checkRows() || checkColumns()
        }

        fun unmarkedSum() = marks.zip(board).sumOf { (marks, row) -> row.filterIndexed { idx, _ -> !marks[idx] }.sum() }

        private fun checkRows(): Boolean {
            return marks.any { row -> row.all { it } }
        }

        private fun checkColumns(): Boolean {
            return (0 until 5).any { colIdx -> marks.all { row -> row[colIdx] } }
        }

        override fun toString(): String {
            return board.mapIndexed { rowIdx, row ->
                row.mapIndexed { colIdx, i ->
                    if (marks[rowIdx][colIdx]) i.bold(color = "33")
                    else i.toString()
                }
                    .joinToString(separator = "\t")
            }
                .joinToString(separator = "\n")
        }
    }
}
