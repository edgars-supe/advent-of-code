package lv.esupe.aoc.solver

class StringInputProvider(private val lines: List<String>) : InputProvider {

    override fun provideInput(year: Int, day: Int): List<String> {
        return lines
    }
}
