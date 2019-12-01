package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day12() }

class Day12 : Puzzle<Int, Long>(2018, 12) {
    override val input = rawInput
    private val EMPTY = false
    private val FILLED = true
    private val plants = getInitialBoolean(input.first().substring(15))
    private val rules = getRules(input.drop(2))

    override fun solvePartOne(): Int {
        return plants.evolve(20).score()
    }

    override fun solvePartTwo(): Long {
        val states = plants.evolve(180)
        val score = states.score()
        val perEvolution = states.map { it.value }.count { it == FILLED }
        return (50_000_000_000 - 180) * perEvolution + score
    }

    private fun Map<Int, Boolean>.evolve(times: Long): Map<Int, Boolean> {
        return (1L..times).fold(this.toMutableMap()) { acc, _ ->
            mutableMapOf<Int, Boolean>().also {
                val lowest = acc.keys.min()!! - 2
                val highest = acc.keys.max()!! + 2
                (lowest..highest).forEach { index ->
                    it[index] = acc.findMatchingRule(index).result
                }
            }
        }
    }

    private fun Map<Int, Boolean>.score(): Int {
        return mapNotNull { (index, state) ->
            if (state == FILLED) index
            else null
        }.sum()
    }

    private fun getInitialBoolean(states: String): MutableMap<Int, Boolean> {
        val map = mutableMapOf<Int, Boolean>()
        states.forEachIndexed { index, c ->
            map[index] = c.toBoolean()
        }
        return map
    }

    private fun getRules(rules: List<String>): List<Rule> =
        rules.map { it.substring(0..4) + it.last() }
            .map { it.map { c -> c.toBoolean() } }
            .map { Rule(it[0], it[1], it[2], it[3], it[4], it[5]) }

    private fun Char.toBoolean() = '#' == this

    private fun MutableMap<Int, Boolean>.getPlantAt(index: Int): Boolean {
        if (get(index) == null) put(index, false)
        return get(index)!!
    }

    private fun MutableMap<Int, Boolean>.findMatchingRule(index: Int): Rule =
        rules.first {
            it.leftmost == getPlantAt(index - 2) &&
                it.left == getPlantAt(index - 1) &&
                it.current == getPlantAt(index) &&
                it.right == getPlantAt(index + 1) &&
                it.rightmost == getPlantAt(index + 2)
        }
}

data class Rule(
    val leftmost: Boolean,
    val left: Boolean,
    val current: Boolean,
    val right: Boolean,
    val rightmost: Boolean,
    val result: Boolean
)
