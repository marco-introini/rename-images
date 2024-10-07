package me.mintdev.models

import me.mintdev.Context

class SidecarCollection {
    val collection: MutableList<Sidecar> = mutableListOf()

    fun add(sidecar: String) {
        val fileExtension = sidecar.substringAfterLast('.', "")
        // extension is NOT an allowed extension for conversions
        // println("Found sidecard $sidecar with extension $fileExtension")
        if (fileExtension.lowercase() !in Context.allowedExtensions)
            collection.add(Sidecar(sidecar))
    }

    fun isEmpty(): Boolean = collection.isEmpty()

    fun isNotEmpty(): Boolean = collection.isNotEmpty()

    fun size(): Int = collection.size

}
