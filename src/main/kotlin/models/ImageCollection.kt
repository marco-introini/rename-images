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

        fun fromListOfImages(list: List<Image>): ImageCollection {
            val imageCollection = ImageCollection()
            list.forEach {
                imageCollection.images.add(it)
            }
            return imageCollection
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
        // filename begins with an ignored string
        if (Context.filePrefixesToIgnore.any { imageName.startsWith(it) }) return false
        val fileExtension = imageName.substringAfterLast('.', "")
        // extension is an allowed extension
        return fileExtension.lowercase() in Context.allowedExtensions
    }

    fun generateNewFileNames() {
        val groupedImages = images.sortedBy { it.datePrefix }.groupBy { it.datePrefix }
        for ((datePrefix, imageList) in groupedImages) {
            println("DATE: $datePrefix")
            var sequenceNumber = 0
            imageList.sortedBy { it.takenDate }.forEach { it.generateNewFileName(++sequenceNumber) }
            for (image in imageList) {
                println(" - ${image.name} -> ${image.newFileName} (${image.fileFormat} - ${image.cameraModel})")
            }
        }
    }

    fun hasDuplicateFilenames(): Boolean {
        return images.groupingBy { it.newFileName }.eachCount().any { it.value > 1 }
    }
}
