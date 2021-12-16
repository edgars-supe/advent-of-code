package lv.esupe.aoc.year2021

import lv.esupe.aoc.DayTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.BitSet

class Day16Test : DayTest<Long, Long>() {
    override val puzzle = { Day16() }

    @Test
    fun day16_part1_tests() {
        testPartOne("D2FE28", 6L)
        testPartOne("38006F45291200", 9L)
        testPartOne("EE00D40C823060", 14L)
        testPartOne("8A004A801A8002F478", 16L)
        testPartOne("620080001611562C8802118E34", 12L)
        testPartOne("C0015000016115A2E0802F182340", 23L)
        testPartOne("A0016C880162017C3686B18A3D4780", 31L)
    }

    @Test
    fun day16_part2_tests() {
        testPartTwo("C200B40A82", 3L)
        testPartTwo("04005AC33890", 54L)
        testPartTwo("880086C3E88112", 7L)
        testPartTwo("CE00C43D881120", 9L)
        testPartTwo("D8005AC2A8F0", 1L)
        testPartTwo("F600BC2D8F", 0L)
        testPartTwo("9C005AC2F8F0", 0L)
        testPartTwo("9C0141080250320F1802104A08", 1L)
    }
}
