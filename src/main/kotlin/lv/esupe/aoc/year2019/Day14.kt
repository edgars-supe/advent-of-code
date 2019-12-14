package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.nearestMultipleAtOrAbove

fun main(args: Array<String>) = solve { Day14() }

class Day14 : Puzzle<Int, Int>(2019, 14) {

    override val input = rawInput.map { line ->
        val reaction = line.split(" => ")
        val output = Constituent(reaction.last())
        val input = reaction.dropLast(1).flatMap { it.split(", ") }.map { Constituent(it) }
        Reaction(input, output)
    }

    override fun solvePartOne(): Int {
        val needs = mutableMapOf<String, Int>()
        calculate(needs, 1, "FUEL")
        return needs["ORE"]!!
    }

    override fun solvePartTwo(): Int = 0

    private fun calculate(store: MutableMap<String, Int>, request: Int, name: String) {
        if (name == "ORE") {
            store["ORE"] = store["ORE"]?.plus(request) ?: request
            return
        }
        val reaction = findReactionFor(name)
        val have = store[name] ?: 0
        if (have >= request) {
            store[name] = have - request
            return
        }

        val adjustedRequest = request - have // use up surplus first
        val nearestMultipleAtOrAbove = adjustedRequest.nearestMultipleAtOrAbove(reaction.output.amount)
        val multiplier = nearestMultipleAtOrAbove / reaction.output.amount

        store[name] = nearestMultipleAtOrAbove - adjustedRequest // surplus after reaction
        reaction.input.forEach { constituent ->
            calculate(store, constituent.amount * multiplier, constituent.name)
        }
    }

    private fun findReactionFor(element: String): Reaction =
        input.find { it.output.name == element } ?: error("Unknown element $element")

    data class Reaction(
        val input: List<Constituent>,
        val output: Constituent
    )

    data class Constituent(
        val amount: Int,
        val name: String
    ) {
        constructor(string: String) : this(string.substringBefore(' ').toInt(), string.substringAfter(' '))
    }
}
