package day07

import BasicDay
import util.FileReader
import kotlin.math.absoluteValue

val root = Directory("/")
val d = FileReader.asStrings("day07.txt")

fun main() {
    Day07.init()
    Day07.run()
}

object Day07 : BasicDay() {

    fun init() = populateFilesystem()

    override fun part1() = root.traverse(100000)

    override fun part2() = root.findMin((70000000 - 30000000 - root.totalSize()).absoluteValue)
}

private fun populateFilesystem() {
    var currentDir = root
    d.forEach {
        if (it.startsWith('$'))
            currentDir = handleCommand(currentDir, it)
        else
            handleContent(currentDir, it)
    }
}

private fun handleCommand(currentDir: Directory, commandString: String): Directory {
    commandString.split(' ').let {
        when (it[1]) {
            "cd" -> {
                return when (it[2]) {
                    "/" -> root
                    ".." -> currentDir.parent!!
                    else -> currentDir.children.first { child -> child.name == it[2] }
                }
            }
            "ls" -> return currentDir
            else -> throw RuntimeException("invalid command")
        }
    }
}

private fun handleContent(currentDir: Directory, contentString: String) {
    contentString.split(' ').let {
        when (it[0]) {
            "dir" -> currentDir.children.add(Directory(name = it[1], parent = currentDir))
            else -> currentDir.files.add(File(name = it[1], size = it[0].toInt()))
        }
    }
}

data class Directory(
    val name: String,
    val parent: Directory? = null,
    val children: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
) {
    fun totalSize(): Int {
        val filesSize = files.sumOf { it.size }
        val childrenDirectorySize = children.sumOf { it.totalSize() }
        return filesSize + childrenDirectorySize
    }

    fun traverse(maxDirectorySizeToConsider: Int): Int {
        val totalSize = totalSize()
        if (totalSize <= maxDirectorySizeToConsider)
            return totalSize + children.sumOf { it.traverse(maxDirectorySizeToConsider) }
        return children.sumOf { it.traverse(maxDirectorySizeToConsider) }
    }

    fun findMin(minSpaceRequired: Int): Int {
        val totalSize = totalSize()
        if (totalSize < minSpaceRequired || children.isEmpty())
            return Int.MAX_VALUE
        return minOf(totalSize, children.minOf { it.findMin(minSpaceRequired) })
    }
}

data class File(val name: String, val size: Int)
