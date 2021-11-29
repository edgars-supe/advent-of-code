package lv.esupe.aoc.utils


fun Char.toIntValue(): Int = code - '0'.code

fun Char.toAlphabetIndex(): Int = lowercaseChar() - 'a'

fun List<Char>.asString(): String = joinToString(separator = "")

fun CharArray.asString(): String = String(this)
