package lv.esupe.aoc.solver

import lv.esupe.aoc.Puzzle
import java.nio.file.Files
import java.nio.file.Paths

abstract class FileInputProvider : InputProvider {

    override fun provideInput(year: Int, day: Int): List<String> {
        return Puzzle::class.java.classLoader
            .getResource(getFileLocation(year, day))
            .toURI()
            .let { Paths.get(it) }
            .let { Files.readAllLines(it) }
    }

    abstract fun getFileLocation(year: Int, day: Int): String
}
