package me.mintdev

import java.io.File

data class Context(
    val workingDirectory: File,
    val filePrefix: String = "",
    val filePrefixesToIgnore: List<String> = emptyList()
)


