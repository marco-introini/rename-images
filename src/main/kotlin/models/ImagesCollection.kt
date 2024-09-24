package me.mintdev.models

class ImagesCollection {
    val images = mutableListOf<String>()

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
