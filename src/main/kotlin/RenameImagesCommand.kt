package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.boolean
import me.mintdev.models.ImageCollection
import java.io.File

class RenameImagesCommand : CliktCommand() {
    val directory: String by argument()
    val dryRun: Boolean by option().boolean().prompt("Dry run?").help("Do not modify file names")

    override fun run() {
        val dir = File(directory)
        val context = Context(
            workingDirectory = File(directory),
            dryRun = dryRun
        )

        if (dir.exists() && dir.isDirectory) {
            println("Working on $directory")
            val imageCollection = ImageCollection.fromDirectory(context)
            println("Found ${imageCollection.images.size} image files.")
            println(imageCollection)
        } else {
            println("The directory is empty or does not contain files.")
        }
    }
}
