package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.boolean
import me.mintdev.models.ImageCollection
import java.io.File

class RenameImagesCommand : CliktCommand() {
    val directory: String by argument()
    val customText: String by option().default("NOVALUE").help("Custom text to use in filename")
    val dryRun: Boolean by option().boolean().prompt("Dry run?").help("Do not modify file names")

    override fun run() {
        val dir = File(directory)
        Context.workingDirectory = File(directory)
        Context.dryRun = dryRun
        Context.customText = customText
        Context.filePrefix = "MIK"

        if (dir.exists() && dir.isDirectory) {
            println("Working on $directory")
            val imageCollection = ImageCollection.fromDirectory(Context.workingDirectory)
            println("Found ${imageCollection.images.size} image files.")
            //println(imageCollection)

            println("=============================")
            imageCollection.generateNewFileNames()
            val imageMap = imageCollection.returnOrderedMap()
            for ((date, images) in imageMap) {
                println("DATE: $date")
                for (image in images) {
                    print("=> ${image.fileName} -> ${image.newFileName} (${image.takenDate} ${image.cameraModel})")
                    for (sidecar in image.sidecars) {
                        print(" SIDECAR: ${sidecar.name}")
                    }
                    if (image.renamed) { print(" SUCCESS") }
                    println()
                }
            }

        } else {
            println("The directory is empty or does not contain files.")
        }
    }
}
