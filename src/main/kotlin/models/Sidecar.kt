package me.mintdev.models

import com.sun.tools.javac.code.TypeAnnotationPosition.field

data class Sidecar(
    val name: String,
    val subdirectory: String = "",
)
