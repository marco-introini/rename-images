package me.mintdev.models

import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Image(
    val name: String,
    var takenDate: Long = 0,
    var formattedDate: String = "",
    var fileFormat: String = "",
    var cameraModel: String = "",
) {

    fun extractMetadata(path: String): Unit {
        val byteArray = File("$path/$name").readBytes()
        val photoMetadata = Kim.readMetadata(byteArray)?.convertToPhotoMetadata()
        //println(photoMetadata)

        takenDate = photoMetadata?.takenDate ?: 0
        formattedDate = formatDate(Date(takenDate))
        cameraModel = photoMetadata?.cameraModel ?: ""
        fileFormat = photoMetadata?.imageFormat.toString()

        println(Date(takenDate))
    }

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyyMMdd")
        return formatter.format(date)
    }

}
