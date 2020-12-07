package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2020, 7) {

    // bright white bags contain 1 shiny gold bag.
    // vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    // faded blue bags contain no other bags.
    override val input = rawInput.map { line ->
        val name = line.substringBefore(" bags c")
        val rules = line.substringAfter("s contain ")
            .split(", ")
            .mapNotNull { rule ->
                if (rule.startsWith("no")) return@mapNotNull null

                val quantity = rule.substringBefore(' ').toInt()
                val bagName = rule.substringAfter(' ').substringBefore(" bag")
                Bag(quantity, bagName)
            }
            .associate { rule -> rule.name to rule.quantity }
        BagRule(name, rules)
    }

    override fun solvePartOne(): Int {
        return input.find("shiny gold").count()
    }

    override fun solvePartTwo(): Int {
        return input.insideBags("shiny gold") - 1 // we don't need to count the outermost bag.
    }

    private fun List<BagRule>.find(name: String): Set<String> = this
        .mapNotNull { rule ->
            if (rule.rules.containsKey(name)) find(rule.name) + rule.name
            else null
        }
        .flatten()
        .toSet()

    private fun List<BagRule>.insideBags(name: String): Int {
        return 1 + first { it.name == name }
            .rules
            .map { (name, quantity) -> quantity * insideBags(name) }
            .sum()
    }

    data class Bag(val quantity: Int, val name: String)

    data class BagRule(val name: String, val rules: Map<String, Int>)
}
