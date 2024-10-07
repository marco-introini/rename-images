package me.mintdev.models

import me.mintdev.Context
import java.io.File
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty

class ImageCollection {
    val images = mutableListOf<Image>()

    companion object {
        fun fromDirectory(
            directory: File,
        ): ImageCollection {
            val imagesCollection = ImageCollection()
            val files = directory.listFiles()

            if (files != null && files.isNotEmpty()) {
                files.forEach { file ->
                    imagesCollection.add(file.name)
                }
                return imagesCollection
            }
            throw Exception("Directory not found or is empty.")
        }

        fun fromListOfImages(imageList: List<Image>): ImageCollection {
            val imageCollection = ImageCollection()
            imageList.forEach {
                imageCollection.images.add(it)
            }
            return imageCollection
        }
    }

    fun add(imageName: String) {
        if (isValidImage(imageName)) {
            val image = Image(imageName)
            image.extractMetadata()
            image.loadSidecars()
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
        // filename begins with an ignored string
        if (Context.filePrefixesToIgnore.any { imageName.startsWith(it) }) return false
        val fileExtension = imageName.substringAfterLast('.', "")
        // extension is an allowed extension
        return fileExtension.lowercase() in Context.allowedExtensions
    }

    fun generateNewFileNames() {
        val groupedImages = images.sortedBy { it.datePrefix }.groupBy { it.datePrefix }
        for ((datePrefix, imageList) in groupedImages) {
            var sequenceNumber = 0
            imageList.sortedBy { it.takenDate }
                .forEach { it.generateNewFileName(++sequenceNumber, Context.customText) }
        }
    }

    fun returnOrderedMap(): Map<String, List<Image>> {
        val groupedImages = images.sortedBy { it.datePrefix }.groupBy { it.datePrefix }
        val map = mutableMapOf<String, List<Image>>()
        for ((datePrefix, imageList) in groupedImages) {
            val imageInDate = imageList.sortedBy { it.takenDate }
            map[datePrefix] = imageInDate
        }
        return map
    }

    fun hasDuplicateFilenames(): Boolean {
        return images.groupingBy { it.newFileName }.eachCount().any { it.value > 1 }
    }
}
