package me.mintdev.models

import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import me.mintdev.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Image(
    val name: String,
    var takenDate: Long = 0,
    var newFileName: String = "",
    var datePrefix: String = "",
    var fileFormat: String = "",
    var cameraModel: String = "",
    var renamed: Boolean = false,
) {

    fun extractMetadata(): Unit {
        val byteArray = File("${Context.workingDirectory.absolutePath}/$name").readBytes()
        val photoMetadata = Kim.readMetadata(byteArray)?.convertToPhotoMetadata()

        takenDate = photoMetadata?.takenDate ?: 0
        datePrefix = formatDate(Date(takenDate))
        cameraModel = photoMetadata?.cameraModel ?: ""
        fileFormat = photoMetadata?.imageFormat.toString()
    }

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyyMMdd")
        return formatter.format(date)
    }

    fun generateNewFileName(): Unit {
        newFileName = "${Context.filePrefix}${datePrefix}_XXXX_"
    }

}
