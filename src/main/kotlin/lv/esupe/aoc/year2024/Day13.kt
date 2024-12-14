package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.PointL
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day13() }

class Day13 : Puzzle<Long, Long>(2024, 13) {
    override val input = rawInput.chunkedBy { it.isEmpty() }
        .map { (aLine, bLine, targetLine) -> Machine.fromLines(aLine, bLine, targetLine) }

    override fun solvePartOne(): Long {
        return input.sumOf { it.tokens }
    }

    override fun solvePartTwo(): Long {
        val moveBy = 10_000_000_000_000
        return input
            .map { it.copy(target = it.target.move(moveBy, moveBy)) }
            .sumOf { it.tokens }
    }

    data class Machine(
        val da: PointL,
        val db: PointL,
        val target: PointL
    ) {
        val tokens: Long
        
        init {
            val b = (da.x * target.y - da.y * target.x) / (da.x * db.y - da.y * db.x)
            val a = (target.y - db.y * b) / da.y
            val check = da.y * a + db.y * b == target.y && da.x * a + db.x * b == target.x
            tokens = if (check) 3 * a + b else 0
        }

        companion object {
            fun fromLines(aLine: String, bLine: String, targetLine: String,): Machine {
                val da = readLine(aLine, '+')
                val db = readLine(bLine, '+')
                val target = readLine(targetLine, '=')
                return Machine(da, db, target)
            }

            private fun readLine(line: String, sign: Char): PointL {
                return PointL(
                    line.substringAfter("X$sign").substringBefore(",").toLong(),
                    line.substringAfter("Y$sign").toLong(),
                )
            }
        }
    }
}
