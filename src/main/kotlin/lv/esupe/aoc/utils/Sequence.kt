package lv.esupe.aoc.utils

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }