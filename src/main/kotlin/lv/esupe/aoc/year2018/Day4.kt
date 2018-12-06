package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle


fun main(args: Array<String>) = Day4().solve()

class Day4 : Puzzle<Int, Int>(2018, 4) {
    override val input = rawInput.toSleepMap()

    override fun solvePartOne(): Int =
        input.maxBy { it.value.sum() }
            ?.let { it.key.toInt() * it.value.indexOf(it.value.max()!!) }
            ?: throw Exception("Grinch stole Christmas")

    override fun solvePartTwo(): Int =
        input.maxBy { it.value.max()!! }
            ?.let { it.key.toInt() * it.value.indexOf(it.value.max()!!) }
            ?: throw Exception("Grinch stole Christmas")
}

class Log(
    val date: String,
    entry: String
) {
    val minutes = date.takeLast(2).toInt()
    val entry: Entry = entry.parseEntry()

    private fun String.parseEntry(): Entry =
        when {
            this == "wakes up" -> Entry.Wake()
            this == "falls asleep" -> Entry.Sleep()
            else -> Entry.Start(this.split(" ")[1].substring(1))
        }

}

sealed class Entry {
    class Wake : Entry()
    class Sleep : Entry()
    class Start(val id: String) : Entry()
}

private fun getMinuteList(): MutableList<Int> =
    mutableListOf<Int>().apply {
        for (i in 0..59) add(0)
    }

private fun String.toLog(): Log? =
    Regex("""\[([0-9-: ]*)] (.*)""")
        .find(this)
        ?.groupValues
        ?.let { Log(it[1], it[2]) }

private fun List<String>.toSleepMap() =
    mapNotNull { it.toLog() }
        .sortedBy { it.date }
        .mapSleep()

private fun List<Log>.mapSleep(): Map<String, List<Int>> {
    val sleepMap = mutableMapOf<String, MutableList<Int>>()
    var currentId = "-1"
    var sleepStartTime = -1
    forEach {
        when (it.entry) {
            is Entry.Start -> currentId = it.entry.id
            is Entry.Sleep -> sleepStartTime = it.minutes
            is Entry.Wake -> {
                for (i in sleepStartTime until it.minutes) {
                    val list = sleepMap.getOrDefault(currentId, getMinuteList())
                    list[i]++
                    sleepMap[currentId] = list
                }
            }
        }
    }
    return sleepMap
}