package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day15() }

class Day15 : Puzzle<Int, Int>(2023, 15) {
    override val input = rawInput[0].split(",")

    override fun solvePartOne(): Int {
        return input.sumOf { hash(it) }
    }

    override fun solvePartTwo(): Int {
        val boxes = Array(256) { ArrayDeque<Pair<String, Int>>() }
        input.forEach { step ->
            if (step.last() == '-') {
                val stepLabel = step.dropLast(1)
                val hash = hash(stepLabel)
                boxes[hash].removeIf { (label) -> label == stepLabel }
            } else {
                val (stepLabel, focalLength) = step.split("=")
                val hash = hash(stepLabel)
                val idx = boxes[hash].indexOfFirst { (label, _) -> label == stepLabel }
                if (idx != -1) {
                    boxes[hash][idx] = stepLabel to focalLength.toInt()
                } else {
                    boxes[hash].add(stepLabel to focalLength.toInt())
                }
            }
        }
        return boxes.withIndex()
            .sumOf { (boxIdx, box) ->
                box.withIndex()
                    .sumOf { (lensIdx, data) ->
                        val (_, focalLength) = data
                        (lensIdx + 1) * focalLength * (boxIdx + 1)
                    }
            }
    }

    private fun hash(string: String): Int {
        return string.fold(0) { acc, char -> ((acc + char.code) * 17) % 256 }
    }
}
