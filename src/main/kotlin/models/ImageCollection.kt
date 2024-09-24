package me.mintdev.models

import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import java.io.File

class ImageCollection() {
    val images = mutableListOf<String>()

    companion object {
        fun fromDirectory(directory: String): ImageCollection {
            val dir = File(directory)
            val imagesCollection = ImageCollection()
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

    fun add(image: String) {
        if (isValidImage(image)) {
            images.add(image)
        }
    }

    fun remove(image: String) {
        images.remove(image)
    }

    override fun toString(): String {
        return images.joinToString("\n") { it }
    }

    private fun isValidImage(image: String): Boolean {
        val extension = image.substringAfterLast('.', "")
        val allowedExtensions = listOf("jpg", "jpeg", "png", "heic")
        return extension.lowercase() in allowedExtensions
    }
}
