package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy
import lv.esupe.aoc.utils.product

fun main() = solve { Day11() }

class Day11 : Puzzle<Long, Long>(2022, 11) {

    override val input = rawInput
        .chunkedBy { it.isBlank() }
        .map { lines -> Monkey.from(lines) }

    override fun solvePartOne(): Long {
        val monkeys = input.map { it.copy() }
        return calculateMonkeyBusiness(monkeys, rounds = 20) { it / 3 }
    }

    override fun solvePartTwo(): Long {
        val monkeys = input.map { it.copy() }
        val mod = monkeys.map { it.divisibleBy }.reduce { a, b -> a * b }
        return calculateMonkeyBusiness(monkeys, rounds = 10000) { it % mod }
    }

    private fun calculateMonkeyBusiness(monkeys: List<Monkey>, rounds: Int, calmDown: (Long) -> Long): Long {
        repeat(rounds) {
            monkeys.forEach { it.inspectItems(monkeys, calmDown) }
        }
        return monkeys
            .map { it.itemInspectionCount }
            .sortedDescending()
            .take(2)
            .product()
    }

    data class Monkey(
        private val startingItems: List<Long>,
        private val operation: (old: Long) -> Long,
        val divisibleBy: Long,
        private val ifTrueMonkey: Int,
        private val ifFalseMonkey: Int
    ) {
        var itemInspectionCount = 0L
            private set
        private val items = ArrayDeque(startingItems)

        private fun receiveItem(value: Long) {
            items.add(value)
        }

        fun inspectItems(monkeys: List<Monkey>, calmDown: (Long) -> Long) {
            while (items.isNotEmpty()) {
                val inspect = items.removeFirst()
                itemInspectionCount++
                val afterInspection = operation(inspect)
                val afterCalmDown = calmDown(afterInspection)
                val giveToMonkey =
                    if (afterCalmDown % divisibleBy == 0L) ifTrueMonkey
                    else ifFalseMonkey
                monkeys[giveToMonkey].receiveItem(afterCalmDown)
            }
        }

        companion object {
            fun from(lines: List<String>): Monkey {
                val startingItems = lines[1]
                    .removePrefix("  Starting items: ")
                    .split(", ")
                    .map { it.toLong() }

                val operation = lines[2]
                    .removePrefix("  Operation: new = old ")
                    .split(' ')
                    .let { (operator, n) ->
                        when (operator) {
                            "+" -> { old: Long -> old + n.toLong() }
                            "*" -> {
                                if (n == "old") { old: Long -> old * old }
                                else { old: Long -> old * n.toLong() }
                            }
                            else -> error("Unknown operator $operator")
                        }
                    }

                val divisibleBy = lines[3].substringAfterLast(' ').toLong()
                val ifTrue = lines[4].substringAfterLast(' ').toInt()
                val ifFalse = lines[5].substringAfterLast(' ').toInt()

                return Monkey(startingItems, operation, divisibleBy, ifTrue, ifFalse)
            }
        }
    }
}
