package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import me.mintdev.models.ImageCollection
import java.io.File

class RenameImagesCommand : CliktCommand() {
    val directory: String by argument()

    override fun run() {
        val dir = File(directory)
        val context = Context(File(directory))
        val imageCollection: ImageCollection
        // Check if the directory exists and if it is a directory (not a file)
        if (dir.exists() && dir.isDirectory) {
            println("Existing directory: $directory")
            imageCollection = ImageCollection.fromDirectory(context)
            println("Found ${imageCollection.images.size} files.")
            println(imageCollection)
        } else {
            println("The directory is empty or does not contain files.")
        }
    }
}
