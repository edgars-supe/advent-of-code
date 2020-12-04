package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2020, 4) {
    companion object {
        private val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        private val hclRegex = Regex("""^#[0-9a-f]{6}$""")
        private val eclValues = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        private val pidRegex = Regex("""^[0-9]{9}$""")
    }

    override val input = rawInput.processInput()

    override fun solvePartOne(): Int = input.count { it.isValid(requiredFields) }

    override fun solvePartTwo(): Int = input.count { it.isValidStrict(requiredFields) }

    private fun List<String>.processInput(): List<Passport> = this
        .fold(mutableListOf(mutableListOf<String>())) { acc, s ->
            if (s.isNotBlank()) acc.last().addAll(s.split(" "))
            else acc.add(mutableListOf())
            acc
        }
        .map { it.toPassport() }

    private fun List<String>.toPassport(): Passport = this
        .map { it.split(":").let { (key, value) -> key to value } }
        .toMap()
        .let { Passport(it) }

    data class Passport(val fields: Map<String, String>) {
        fun isValid(requiredFields: Set<String>): Boolean = requiredFields.intersect(fields.keys) == requiredFields

        fun isValidStrict(requiredFields: Set<String>): Boolean {
            if (!isValid(requiredFields)) return false

            return fields.entries.all { (key, value) ->
                when (key) {
                    "byr" -> value.toInt() in 1920..2002
                    "iyr" -> value.toInt() in 2010..2020
                    "eyr" -> value.toInt() in 2020..2030
                    "hgt" -> when (value.takeLast(2)) {
                        "cm" -> value.dropLast(2).toInt() in 150..193
                        "in" -> value.dropLast(2).toInt() in 56..76
                        else -> false
                    }
                    "hcl" -> hclRegex.matches(value)
                    "ecl" -> value in eclValues
                    "pid" -> pidRegex.matches(value)
                    "cid" -> true
                    else -> false
                }
            }
        }
    }
}
