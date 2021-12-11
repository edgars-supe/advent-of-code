package lv.esupe.aoc.year2015

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2015, 2) {
    override val input = rawInput.map { line ->
        val (l, w, h) = line.split("x")
        Box(l.toInt(), w.toInt(), h.toInt())
    }

    override fun solvePartOne(): Int {
        return input.sumOf { box ->
            val sideA: Int = box.l * box.w
            val sideB: Int = box.w * box.h
            val sideC: Int = box.h * box.l
            2 * sideA + 2 * sideB + 2 * sideC + minOf(sideA, sideB, sideC)
        }
    }

    override fun solvePartTwo(): Int {
        return input.sumOf { box ->
            val volume: Int = box.l * box.w * box.h
            val smallestPerimeter = (box.l + box.w + box.h - maxOf(box.l, box.w, box.h)) * 2
            smallestPerimeter + volume
        }
    }

    data class Box(val l: Int, val w: Int, val h: Int)
}
