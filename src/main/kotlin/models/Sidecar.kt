package me.mintdev.models

import me.mintdev.services.renameFile

data class Sidecar(
    val name: String,
    val subdirectory: String = "",
) {
    fun rename(newBaseFileName: String) {
        val fileExtension = name.substringAfterLast('.', "")
        val baseFileName = newBaseFileName.substringBeforeLast('.')
        val newFileName = "$baseFileName.$fileExtension"

        renameFile(name, newFileName)
    }
}
