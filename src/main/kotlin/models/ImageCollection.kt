package me.mintdev.models

import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import java.io.File

class ImageCollection() {
    val images = mutableListOf<Image>()
    var basePath: String = ""

    companion object {
        fun fromDirectory(directory: String): ImageCollection {
            val dir = File(directory)
            val imagesCollection = ImageCollection()
            imagesCollection.basePath = directory
            // Get the list of all files in the directory
            val files = dir.listFiles()

            // Check if there are files in the directory
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
            image.extractMetadata(basePath)
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
        val extension = imageName.substringAfterLast('.', "")
        val allowedExtensions = listOf("jpg", "jpeg", "png", "heic")
        return extension.lowercase() in allowedExtensions
    }
}
