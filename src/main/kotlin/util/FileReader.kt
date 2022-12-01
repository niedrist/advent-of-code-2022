package util

import java.io.File

object FileReader {
    fun readResource(path: String) = File(ClassLoader.getSystemResource(path).file).readText()

    fun asStrings(path: String) = readResource(path).split("\n")
    fun asInts(path: String) = asStrings(path).map { it.toInt() }
}