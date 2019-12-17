package lv.esupe.aoc.year2019.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import lv.esupe.aoc.utils.toIntValue
import lv.esupe.aoc.year2019.model.Intcode.Param.*

private typealias Memory = MutableMap<Long, Long>

fun String.toProgram() = split(",").map { it.toLong() }

@ExperimentalCoroutinesApi
class Intcode(
    program: List<Long>,
    val input: Channel<Long> = Channel(Channel.UNLIMITED),
    val output: Channel<Long> = Channel(Channel.UNLIMITED)
){

    var onInput: (suspend Channel<Long>.() -> Unit)? = null
    private val memory: Memory = program.withIndex()
        .associateTo(mutableMapOf()) { (idx, value) -> idx.toLong() to value }
        .withDefault { 0L }
    private var pointer = 0L
    private var relativeBase = 0L
    private val opcode: Long
        get() = memory.getOrPut(pointer) { 0 }

    suspend fun input(vararg values: Long): Intcode {
        values.forEach { input.send(it) }
        return this
    }

    suspend fun execute(noun: Long? = null, verb: Long? = null, input: Long): List<Long> =
        execute(noun, verb, listOf(input))

    suspend fun execute(noun: Long? = null, verb: Long? = null, inputs: List<Long>): List<Long> {
        inputs.forEach { input.send(it) }
        execute(noun, verb)
        return output.toList()
    }

    suspend fun execute(noun: Long? = null, verb: Long? = null): Long {
        noun?.let { memory[1] = it }
        verb?.let { memory[2] = it }

        var op = Op.from(opcode)
        while (op != Op.Halt) {
            pointer += when (op) {
                Op.Add -> add()
                Op.Multiply -> multiply()
                Op.Input -> input()
                Op.Output -> output()
                Op.JumpIfTrue -> jumpIfTrue()
                Op.JumpIfFalse -> jumpIfFalse()
                Op.LessThan -> lessThan()
                Op.Equals -> equals()
                Op.AdjustRelative -> adjustRelative()
                Op.Halt -> halt()
            }
            op = Op.from(opcode)
        }
        halt()
        return memory[0] ?: Long.MIN_VALUE
    }

    private fun add(): Long {
        val result = read(First) + read(Second)
        write(Third, result)
        return 4
    }

    private fun multiply(): Long {
        val result = read(First) * read(Second)
        write(Third, result)
        return 4
    }

    private suspend fun input(): Long = coroutineScope {
        launch { onInput?.invoke(input) }
        launch {
            val value = input.receive()
            write(First, value)
        }
        2L
    }

    private suspend fun output(): Long {
        val value = read(First)
        if (!output.isClosedForReceive) output.send(value)
        return 2
    }

    private fun jumpIfTrue(): Long {
        return if (read(First) != 0L) read(Second) - pointer
        else 3
    }

    private fun jumpIfFalse(): Long {
        return if (read(First) == 0L) read(Second) - pointer
        else 3
    }

    private fun lessThan(): Long {
        if (read(First) < read(Second)) write(Third, 1)
        else write(Third, 0)
        return 4
    }

    private fun equals(): Long {
        if (read(First) == read(Second)) write(Third, 1)
        else write(Third, 0)
        return 4
    }

    private fun adjustRelative(): Long {
        relativeBase += read(First)
        return 2
    }

    private fun halt(): Long {
        output.close()
        return 0
    }

    private fun read(param: Param): Long {
        val parameterValue = memory[pointer + param.index + 1]
            ?: error("Unable to find parameter, opcode $opcode, parameter #$param")
        return when (Mode.from(opcode, param)) {
            Mode.Parameter -> memory[parameterValue]
            Mode.Immediate -> parameterValue
            Mode.Relative -> memory[relativeBase + parameterValue]
        }
            ?: 0L
    }

    private fun write(param: Param, value: Long) {
        val parameterValue = memory[pointer + param.index + 1]
            ?: error("Unable to find parameter, opcode $opcode, parameter #$param")
        when (val mode = Mode.from(opcode, param)) {
            Mode.Parameter -> memory[parameterValue] = value
            Mode.Relative -> memory[relativeBase + parameterValue] = value
            else -> error("Invalid parameter mode $mode for writing, opcode $opcode, parameter #$param")
        }
    }

    private enum class Op(val opcode: Long) {
        Add(1),
        Multiply(2),
        Input(3),
        Output(4),
        JumpIfTrue(5),
        JumpIfFalse(6),
        LessThan(7),
        Equals(8),
        AdjustRelative(9),
        Halt(99);

        companion object {
            fun from(opcode: Long): Op = values()
                .firstOrNull { it.isOp(opcode) }
                ?: error("Invalid opcode, $opcode")
        }

        fun isOp(code: Long) = code.rem(100) == opcode
    }

    private enum class Mode(val code: Int) {
        Parameter(0),
        Immediate(1),
        Relative(2);

        companion object {
            fun from(opcode: Long, param: Param): Mode = opcode.toString()
                .reversed()
                .getOrElse(param.index + 2) { '0' }
                .toIntValue()
                .let { mode -> values().firstOrNull { it.code == mode } }
                ?: error("Invalid parameter mode for param #$param in opcode $opcode")
        }
    }

    private enum class Param(val index: Int) {
        First(0),
        Second(1),
        Third(2)
    }
}
