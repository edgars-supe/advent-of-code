package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2021, 12) {
    private val caves = mutableMapOf<String, Cave>()

    override val input = rawInput.forEach { line ->
        val (from, to) = line.split("-")
        val fromCave = caves.getOrPut(from) { Cave(from) }
        val toCave = caves.getOrPut(to) { Cave(to) }
        fromCave.connected.add(toCave)
        toCave.connected.add(fromCave)
    }

    override fun solvePartOne(): Int {
        val visited = mutableSetOf<Cave>()
        val start = caves["start"] ?: Cave("start")
        val target = caves["end"] ?: Cave("end")
        val paths = mutableSetOf<List<Cave>>()
        traverse(visited, start, target, null, mutableListOf(), paths)
        return paths.count()
    }

    override fun solvePartTwo(): Int {
        val paths = mutableSetOf<List<Cave>>()
        val start = caves["start"] ?: Cave("start")
        val target = caves["end"] ?: Cave("end")
        caves.filter { (name, _) -> name.lowercase() == name }
            .filter { (_, cave) -> cave != start && cave != target }
            .values
            .forEach { double ->
                val visited = mutableSetOf<Cave>()
                traverse(visited, start, target, double, mutableListOf(), paths)
            }
        return paths.count()
    }

    private fun traverse(
        visited: MutableSet<Cave>,
        current: Cave,
        target: Cave,
        double: Cave?,
        path: MutableList<Cave>,
        paths: MutableSet<List<Cave>>
    ) {
        path.add(current)
        if (current.name.uppercase() != current.name && current.name != "end" && current.name != double?.name) {
            visited.add(current)
        }
        if (current == target) {
            paths.add(path)
            return
        }
        current.connected.filter { it !in visited }
            .forEach { traverse(visited.toMutableSet(), it, target, double.takeIf { current != double }, path.toMutableList(), paths) }
    }

    data class Cave(val name: String) {
        val connected = mutableSetOf<Cave>()
    }
}
