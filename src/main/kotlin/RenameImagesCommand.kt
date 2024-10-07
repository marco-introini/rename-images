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
    val filePrefix: String by option().default("MIK").help("File prefix to use in renamed filename")
    val filePrefixToIgnore: String by option().default("MI").help("File prefix to ignore")
    val fileExtension: String by option().default("jpg,jpeg,png,heic").help("File extension to rename")
    val dryRun: Boolean by option().boolean().prompt("Dry run?").help("Do not modify file names (dry run mode)")

    override fun run() {
        val dir = File(directory)
        Context.workingDirectory = File(directory)
        Context.dryRun = dryRun
        Context.customText = customText
        Context.filePrefix = filePrefix
        Context.filePrefixesToIgnore = filePrefixToIgnore.splitToList()
        Context.allowedExtensions = fileExtension.splitToList()

        if (dir.exists() && dir.isDirectory) {
            println("Working on $directory")
            val imageCollection = ImageCollection.fromDirectory(Context.workingDirectory)
            println("Found ${imageCollection.images.size} image files.")
            println("Skipped ${imageCollection.skippedImages.size} images.")

            title("Checking Images")
            imageCollection.generateNewFileNames()
            val imageMap = imageCollection.returnOrderedMap()
            for ((date, images) in imageMap) {
                println("DATE: $date")
                for (image in images) {
                    print("=> ${image.fileName} -> ${image.newFileName} (${image.takenDate} ${image.cameraModel} Sidecar: ${image.sidecars.size()})")
                    for (sidecar in image.sidecars.collection) {
                        print(" SIDECAR: ${sidecar.name}")
                    }
                    if (image.renamed) {
                        print(" SUCCESS")
                    }
                    println()
                }
            }

            title("Checking Structure")

            print("Has no duplicate image name: ")

            if (imageCollection.hasDuplicateFilenames()) {
                println("FALSE")
                return
            }
            else {
                println("TRUE")
            }

            print("No new file will collide with a current file name: ")
            if (imageCollection.createFileAlreadyExisting()) {
                println("FALSE")
                return
            } else {
                println("TRUE")
            }

            title("Renaming Images")


        } else {
            println("The directory is empty or does not contain files.")
        }
    }
}

fun title(message: String): Unit {
    println("=============================")
    println(message)
    println("=============================")
}
