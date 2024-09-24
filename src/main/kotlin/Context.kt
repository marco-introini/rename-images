package me.mintdev

import java.io.File

data class Context(
    val workingDirectory: File,
    val dryRun: Boolean = false,
    val filePrefix: String,
    val customText: String,
    val filePrefixesToIgnore: List<String> = emptyList(),
    val allowedExtensions: List<String> = listOf("jpg", "jpeg", "png", "heic"),
)


