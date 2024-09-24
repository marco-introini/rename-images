package me.mintdev

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

class RenameImagesCommand: CliktCommand() {
    val directory: String by argument()

    override fun run() {
        println("Working directory: $directory")
    }
}
