package day10

import BasicDay
import util.FileReader

val d = FileReader.asStrings("day10.txt").map {
    it.split(" ")
}

fun main() = Day10.run()

object Day10 : BasicDay() {
    override fun part1(): Int {
        var cycleCounter = 0
        var xRegister = 1
        var signalStrength = 0
        d.forEach { instruction ->
            when (instruction[0]) {
                "noop" -> {
                    cycleCounter++
                    signalStrength += getSignalStrength(cycleCounter, xRegister)
                }
                "addx" -> {
                    cycleCounter++
                    signalStrength += getSignalStrength(cycleCounter, xRegister)

                    cycleCounter++
                    signalStrength += getSignalStrength(cycleCounter, xRegister)

                    xRegister += instruction[1].toInt()
                }
            }

        }
        return signalStrength
    }

    override fun part2(): String {
        var cycleCounter = 0
        var xRegister = 1
        val imageBuilder = StringBuilder()

        d.forEach { instruction ->
            when (instruction[0]) {
                "noop" -> {
                    imageBuilder.append(drawPixels(cycleCounter, xRegister))
                    cycleCounter++
                }
                "addx" -> {
                    imageBuilder.append(drawPixels(cycleCounter, xRegister))
                    cycleCounter++

                    imageBuilder.append(drawPixels(cycleCounter, xRegister))
                    cycleCounter++

                    xRegister += instruction[1].toInt()
                }
            }

        }
        return imageBuilder.toString().chunked(40).joinToString("\n") { it }
    }
}

private fun drawPixels(cycleCounter: Int, xRegister: Int): Char {
    val spritePosition = (xRegister - 1)..(xRegister + 1)
    if (spritePosition.contains(cycleCounter.mod(40)))
        return '#'
    return ' '
}

private fun getSignalStrength(cycleCounter: Int, xRegister: Int): Int {
    if (cycleCounter.mod(40) == 20)
        return cycleCounter * xRegister
    return 0
}