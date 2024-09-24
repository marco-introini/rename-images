package me.mintdev

import com.github.ajalt.clikt.core.main
import java.io.File

object Context{
    lateinit var workingDirectory: File
    var dryRun: Boolean = false
    var filePrefix: String = ""
    var customText: String = ""
    var filePrefixesToIgnore: List<String> = emptyList()
    var allowedExtensions: List<String> = listOf("jpg", "jpeg", "png", "heic")
}

fun main(args: Array<String>) {
    RenameImagesCommand().main(args)
}
