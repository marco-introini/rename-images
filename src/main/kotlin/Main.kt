package me.mintdev

import com.github.ajalt.clikt.core.main
import java.io.File

object Context {
    lateinit var workingDirectory: File
    var dryRun: Boolean = false
    var filePrefix: String = ""
    var customText: String = ""
    var filePrefixesToIgnore: List<String> = listOf("MI")
    var allowedExtensions: List<String> = listOf("jpg", "jpeg", "png", "heic")
    var numberOfDigitsInSequence: Int = 4
}

fun main(args: Array<String>) {
    RenameImagesCommand().main(args)
}

fun String.splitToList(): List<String> = split(",").map { it.trim() }
