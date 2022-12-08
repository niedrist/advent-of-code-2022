package day08

import BasicDay
import util.FileReader

val d = FileReader.asStrings("day08.txt").map { it.split("").filter { c -> c.isNotEmpty() }.map { c -> c.toInt() } }

fun main() = Day08.run()

object Day08 : BasicDay() {
    override fun part1(): Int {
        var visibleTreeCount = 0
        for (y in d.indices)
            for (x in d[y].indices)
                if (isVisible(x, y))
                    visibleTreeCount++
        return visibleTreeCount
    }

    override fun part2(): Int {
        var highestScenicScore = 0
        for (y in d.indices)
            for (x in d[y].indices)
                if (visibilityCount(x, y) > highestScenicScore) {
                    highestScenicScore = visibilityCount(x, y)
                }
        return highestScenicScore
    }
}

private fun isVisible(x: Int, y: Int) = isVisibleUp(d[y][x], x, y) ||
        isVisibleDown(d[y][x], x, y) ||
        isVisibleLeft(d[y][x], x, y) ||
        isVisibleRight(d[y][x], x, y)

private fun isVisibleUp(treeHeight: Int, x: Int, y: Int): Boolean {
    if (x == 0) return true
    if (d[y][x - 1] >= treeHeight) return false
    return isVisibleUp(treeHeight, x - 1, y)
}

private fun isVisibleDown(treeHeight: Int, x: Int, y: Int): Boolean {
    if (x == d[y].size - 1) return true
    if (d[y][x + 1] >= treeHeight) return false
    return isVisibleDown(treeHeight, x + 1, y)
}

private fun isVisibleLeft(treeHeight: Int, x: Int, y: Int): Boolean {
    if (y == 0) return true
    if (d[y - 1][x] >= treeHeight) return false
    return isVisibleLeft(treeHeight, x, y - 1)
}

private fun isVisibleRight(treeHeight: Int, x: Int, y: Int): Boolean {
    if (y == d.size - 1) return true
    if (d[y + 1][x] >= treeHeight) return false
    return isVisibleRight(treeHeight, x, y + 1)
}


private fun visibilityCount(x: Int, y: Int) = visibilityCountUp(d[y][x], x, y) *
        visibilityCountDown(d[y][x], x, y) *
        visibilityCountLeft(d[y][x], x, y) *
        visibilityCountRight(d[y][x], x, y)

private fun visibilityCountUp(treeHeight: Int, x: Int, y: Int): Int {
    if (x == 0) return 0
    if (d[y][x - 1] >= treeHeight) return 1
    return 1 + visibilityCountUp(treeHeight, x - 1, y)
}

private fun visibilityCountDown(treeHeight: Int, x: Int, y: Int): Int {
    if (x == d[y].size - 1) return 0
    if (d[y][x + 1] >= treeHeight) return 1
    return 1 + visibilityCountDown(treeHeight, x + 1, y)
}

private fun visibilityCountLeft(treeHeight: Int, x: Int, y: Int): Int {
    if (y == 0) return 0
    if (d[y - 1][x] >= treeHeight) return 1
    return 1 + visibilityCountLeft(treeHeight, x, y - 1)
}

private fun visibilityCountRight(treeHeight: Int, x: Int, y: Int): Int {
    if (y == d.size - 1) return 0
    if (d[y + 1][x] >= treeHeight) return 1
    return 1 + visibilityCountRight(treeHeight, x, y + 1)
}