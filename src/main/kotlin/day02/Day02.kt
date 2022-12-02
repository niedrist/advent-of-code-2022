package day02

import BasicDay
import util.FileReader

val d = FileReader.asStrings("day02.txt").map { it.split(" ") }.map { Pair(it[0].single(), it[1].single()) }

fun main() = Day02.run()

object Day02 : BasicDay() {
    override fun part1() = d.sumOf { it.winScore() + it.pairScore() }

    override fun part2() = d.sumOf { with(calcNewPair(it)) { winScore() + pairScore() } }
}

private fun Pair<Char, Char>.winScore() =
    when (this.first) {
        'A' -> when (this.second) {
            'X' -> 3
            'Y' -> 6
            'Z' -> 0
            else -> 0
        }
        'B' -> when (this.second) {
            'X' -> 0
            'Y' -> 3
            'Z' -> 6
            else -> 0
        }
        'C' -> when (this.second) {
            'X' -> 6
            'Y' -> 0
            'Z' -> 3
            else -> 0
        }
        else -> 0
    }


private fun Pair<Char, Char>.pairScore() = when (this.second) {
    'X' -> 1
    'Y' -> 2
    'Z' -> 3
    else -> 0
}

private fun calcNewPair(p: Pair<Char, Char>) =
    when (p.second) {
        'X' -> when (p.first) {
            'A' -> p.copy(second = 'Z')
            'B' -> p.copy(second = 'X')
            'C' -> p.copy(second = 'Y')
            else -> p
        }
        'Y' -> when (p.first) {
            'A' -> p.copy(second = 'X')
            'B' -> p.copy(second = 'Y')
            'C' -> p.copy(second = 'Z')
            else -> p
        }
        'Z' -> when (p.first) {
            'A' -> p.copy(second = 'Y')
            'B' -> p.copy(second = 'Z')
            'C' -> p.copy(second = 'X')
            else -> p
        }
        else -> p
    }

