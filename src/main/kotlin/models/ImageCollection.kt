package me.mintdev.models

import me.mintdev.Context
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty

class ImageCollection(val context: Context) {
    val images = mutableListOf<Image>()

    companion object {
        fun fromDirectory(context: Context): ImageCollection {
            val imagesCollection = ImageCollection(context)
            val files = context.workingDirectory.listFiles()

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
            image.extractMetadata(context)
            image.generateNewFileName(context)
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
        return fileExtension.lowercase() in context.allowedExtensions
    }
}
