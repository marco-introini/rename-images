package me.mintdev.services

import me.mintdev.Context

fun renameFile(source: String, dest: String) {
    val sourceFile = java.io.File(Context.workingDirectory.toString() + source)
    val destFile = java.io.File(Context.workingDirectory.toString() + dest)

    if (sourceFile.exists() && sourceFile.isFile) {
        sourceFile.renameTo(destFile)
    } else {
        println("The source file does not exist or is not a file.")
    }
}
