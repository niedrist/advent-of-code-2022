package day06

import BasicDay
import util.FileReader

val d = FileReader.readResource("day06.txt")

fun main() = Day06.run()

object Day06 : BasicDay() {
    override fun part1() = calculateMarker(4)

    override fun part2() = calculateMarker(14)
}

private fun calculateMarker(count: Int) = d.windowed(count).indexOfFirst { count == it.toSet().size } + count