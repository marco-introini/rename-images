package me.mintdev.models

import me.mintdev.Context
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty

class ImageCollection {
    val images = mutableListOf<Image>()

    companion object {
        fun fromDirectory(): ImageCollection {
            val imagesCollection = ImageCollection()
            val files = Context.workingDirectory.listFiles()

            if (files != null && files.isNotEmpty()) {
                files.forEach { file ->
                    imagesCollection.add(file.name)
                }
                return imagesCollection
            }
            throw Exception("Directory not found or is empty.")
        }
    }

    fun add(imageName: String) {
        if (isValidImage(imageName)) {
            val image = Image(imageName)
            image.extractMetadata()
            images.add(image)
        }
    }

    fun remove(image: Image) {
        images.remove(image)
    }

    override fun toString(): String {
        return images.joinToString("\n") { it.toString() }
    }

    private fun isValidImage(imageName: String): Boolean {
        val fileExtension = imageName.substringAfterLast('.', "")
        return fileExtension.lowercase() in Context.allowedExtensions
    }

    fun generateNewFileNames() {
        val groupedImages = images.groupBy { it.datePrefix }
        for ((datePrefix, imageList) in groupedImages) {
            println("Chiave: $datePrefix")
            var sequenceNumber = 0
            imageList.sortedBy { it.takenDate }.forEach { it.generateNewFileName(++sequenceNumber)}
            for (image in imageList) {
                println(" - Valore: $image")
            }
        }
    }
}
