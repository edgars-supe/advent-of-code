package lv.esupe.aoc.utils


fun Char.toIntValue(): Int = toInt() - 48

fun Char.toAlphabetIndex(): Int = toLowerCase() - 'a'

fun List<Char>.asString(): String = joinToString(separator = "")

fun CharArray.asString(): String = String(this)
