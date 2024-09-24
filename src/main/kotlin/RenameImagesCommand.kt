package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File

class RenameImagesCommand: CliktCommand() {
    val directory: String by argument()

    override fun run() {
        val dir = File(directory)

        // Check if the directory exists and if it is a directory (not a file)
        if (dir.exists() && dir.isDirectory) {
            println("Existing directory: $directory")

            // Get the list of all files in the directory
            val files = dir.listFiles()

            // Check if there are files in the directory
            if (files != null && files.isNotEmpty()) {
                println("List of files in the directory:")
                files.forEach { file ->
                    println(file.name)
                }
            } else {
                println("The directory is empty or does not contain files.")
            }
        } else {
            println("Error: $directory is not an existing directory.")
        }
    }
}
