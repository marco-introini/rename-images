package me.mintdev.services

fun renameFile(source: String, dest: String) {
    val sourceFile = java.io.File(source)
    val destFile = java.io.File(dest)

    if (sourceFile.exists() && sourceFile.isFile) {
        sourceFile.renameTo(destFile)
    } else {
        println("The source file does not exist or is not a file.")
    }
}
