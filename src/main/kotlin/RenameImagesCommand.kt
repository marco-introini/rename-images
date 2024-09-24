package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import me.mintdev.models.ImagesCollection
import java.io.File

class RenameImagesCommand: CliktCommand() {
    val directory: String by argument()

    override fun run() {
        val imagesCollection = ImagesCollection()
        val dir = File(directory)

        // Check if the directory exists and if it is a directory (not a file)
        if (dir.exists() && dir.isDirectory) {
            println("Existing directory: $directory")

            // Get the list of all files in the directory
            val files = dir.listFiles()

            // Check if there are files in the directory
            if (files != null && files.isNotEmpty()) {
                files.forEach { file ->
                    imagesCollection.add(file.name)
                }
                println("Found ${imagesCollection.images.size} files.")
                println(imagesCollection)

            } else {
                println("The directory is empty or does not contain files.")
            }
        } else {
            println("Error: $directory is not an existing directory.")
        }
    }
}
