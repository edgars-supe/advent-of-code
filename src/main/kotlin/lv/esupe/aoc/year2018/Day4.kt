package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import java.text.SimpleDateFormat
import java.util.*


fun main(args: Array<String>) {
    Day4Puzzle1().calculateAndPrint()
    Day4Puzzle2().calculateAndPrint()
}

class Day4Puzzle1 : Puzzle<Int>(2018, 4, 1) {
    override fun calculate(): Int {
        val sleepMap = mutableMapOf<String, MutableList<Int>>()
        input.mapNotNull {
            Regex("""\[([0-9-: ]*)] (.*)""")
                .find(it)
                ?.groupValues
        }
            .map { Log(SimpleDateFormat("yyyy-MM-dd hh:mm").parse(it[1]), it[2]) }
            .sortedBy { it.date }
            .apply {
                var currentId: String = "-1"
                var sleepStartTime = -1
                forEach {
                    when (it.entry) {
                        is Entry.Start -> currentId = it.entry.id
                        is Entry.Sleep -> sleepStartTime = it.date.minutes
                        is Entry.Wake -> {
                            for (i in sleepStartTime until it.date.minutes) {
                                val list = sleepMap.getOrDefault(currentId, getMinuteList())
                                list[i]++
                                sleepMap[currentId] = list
                            }
                        }
                    }
                }
            }
        return sleepMap.maxBy { it.value.sum() }
            ?.let { it.key.toInt() * it.value.indexOf(it.value.max()!!) }
            ?: throw Exception("Grinch stole Christmas")
    }
}

class Day4Puzzle2 : Puzzle<Int>(2018, 4, 2) {
    override fun calculate(): Int {
        val sleepMap = mutableMapOf<String, MutableList<Int>>()
        input.mapNotNull {
            Regex("""\[([0-9-: ]*)] (.*)""")
                .find(it)
                ?.groupValues
        }
            .map { Log(SimpleDateFormat("yyyy-MM-dd hh:mm").parse(it[1]), it[2]) }
            .sortedBy { it.date }
            .apply {
                var currentId: String = "-1"
                var sleepStartTime = -1
                forEach {
                    when (it.entry) {
                        is Entry.Start -> currentId = it.entry.id
                        is Entry.Sleep -> sleepStartTime = it.date.minutes
                        is Entry.Wake -> {
                            for (i in sleepStartTime until it.date.minutes) {
                                val list = sleepMap.getOrDefault(currentId, getMinuteList())
                                list[i]++
                                sleepMap[currentId] = list
                            }
                        }
                    }
                }
            }
        return sleepMap.maxBy { it.value.max()!! }
            ?.let { it.key.toInt() * it.value.indexOf(it.value.max()!!) }
            ?: throw Exception("Grinch stole Christmas")
    }
}

class Log(
    val date: Date,
    entry: String
) {
    val entry: Entry = entry.parseEntry()

    fun String.parseEntry(): Entry =
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
        for (i in 0 .. 59) add(0)
    }
