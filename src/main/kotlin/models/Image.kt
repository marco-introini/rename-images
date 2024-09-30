package me.mintdev.models

import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import me.mintdev.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Image(
    val fileName: String,
    var newFileName: String = "",
    var takenDate: Long = 0,
    var datePrefix: String = "",
    var fileFormat: String = "",
    var cameraModel: String = "",
    val sidecars: Array<Sidecar> = arrayOf(),
    var renamed: Boolean = false,
) {

    fun extractMetadata(): Unit {
        val byteArray = File("${Context.workingDirectory.absolutePath}/$fileName").readBytes()
        val photoMetadata = Kim.readMetadata(byteArray)?.convertToPhotoMetadata()

        takenDate = photoMetadata?.takenDate ?: 0
        datePrefix = formatDate(Date(takenDate))
        cameraModel = photoMetadata?.cameraModel ?: ""
        fileFormat = photoMetadata?.imageFormat.toString()
    }

    fun generateNewFileName(sequenceNumber: Int, customText: String): Unit {
        newFileName = "${Context.filePrefix}${datePrefix}_${customText}_${sequenceNumber.toString().padStart(Context.numberOfDigitsInSequence, '0')}"
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyyMMdd")
        return formatter.format(date)
    }

    fun loadSidecars() {
        val baseFile = fileName.substringBeforeLast('.', "")
        val current = "${Context.workingDirectory.absolutePath}/$fileName"
        // check for all files beginning with current
        // add a sidecar entry for every found file
    }

}
