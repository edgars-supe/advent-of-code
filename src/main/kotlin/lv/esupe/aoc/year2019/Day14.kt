package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.nearestMultipleAtOrAbove

fun main(args: Array<String>) = solve { Day14() }

class Day14 : Puzzle<Long, Long>(2019, 14) {

    override val input = rawInput.map { line ->
        val reaction = line.split(" => ")
        val output = Constituent(reaction.last())
        val input = reaction.dropLast(1).flatMap { it.split(", ") }.map { Constituent(it) }
        Reaction(input, output)
    }

    override fun solvePartOne(): Long {
        val store = mutableMapOf<String, Long>()
        getFuel(store)
        return store["ORE"]!!
    }

    override fun solvePartTwo(): Long {
        return getFuelForOre(0, 1, 1_000_000_000_000)
    }

    private fun getFuel(store: MutableMap<String, Long>) {
        val reaction = findReactionFor("FUEL")
        reaction.input.forEach { constituent ->
            calculate(store, constituent.name, constituent.amount)
        }
        store["FUEL"] = store["FUEL"]?.plus(1) ?: 1
    }

    private fun calculate(store: MutableMap<String, Long>, name: String, request: Long) {
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
            calculate(store, constituent.name, constituent.amount * multiplier)
        }
    }

    private fun findReactionFor(name: String): Reaction =
        input.find { it.output.name == name } ?: error("Unknown element $name")

    private fun getFuelForOre(previous: Long, fuel: Long, target: Long): Long {
        val store = mutableMapOf<String, Long>()
        calculate(store, "FUEL", fuel)
        val result = store["ORE"]!!
        val percentage = (result.toFloat() / target.toFloat()).coerceAtLeast(0.01f)
        return when {
            result > target -> previous
            percentage >= 0.99 -> getFuelForOre(fuel, fuel + 1, target)
            else -> getFuelForOre(fuel, (fuel / percentage).toLong(), target)
        }
    }

    data class Reaction(
        val input: List<Constituent>,
        val output: Constituent
    )

    data class Constituent(
        val name: String,
        val amount: Long
    ) {
        constructor(string: String) : this(string.substringAfter(' '), string.substringBefore(' ').toLong())
    }
}
