package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths


fun getInput(year: Int, day: Int, puzzle: Int): List<String> =
    Any::class.java.classLoader.getResource("year$year/d${day}p$puzzle")
        .toURI()
        .let { Paths.get(it) }
        .let { Files.readAllLines(it) }
