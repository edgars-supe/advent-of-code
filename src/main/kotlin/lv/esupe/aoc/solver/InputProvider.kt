package lv.esupe.aoc.solver

fun interface InputProvider {
    fun provideInput(year: Int, day: Int): List<String>

    companion object {
        var installedInputProvider: InputProvider = DefaultInputProvider()
    }
}
