package lv.esupe.aoc.solver

class DefaultInputProvider : FileInputProvider() {

    override fun getFileLocation(year: Int, day: Int): String {
        return "input/year$year/day$day.in"
    }
}
