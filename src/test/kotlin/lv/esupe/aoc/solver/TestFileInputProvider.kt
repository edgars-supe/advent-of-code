package lv.esupe.aoc.solver

class TestFileInputProvider(
    private val suffix: String
) : FileInputProvider() {

    override fun getFileLocation(year: Int, day: Int): String {
        return "input/year$year/day$day$suffix.in"
    }
}
