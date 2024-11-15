package me.mintdev.models

import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import me.mintdev.Context
import me.mintdev.exceptions.AlreadyRenamedException
import me.mintdev.services.renameFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

data class Image(
    val fileName: String,
    var newFileName: String = "",
    var takenDate: Long = 0,
    var datePrefix: String = "",
    var fileFormat: String = "",
    var cameraModel: String = "",
    var sidecars: SidecarCollection = SidecarCollection(),
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
        val fileExtension = fileName.substringAfterLast('.', "")
        newFileName = "${Context.filePrefix}${datePrefix}_${customText}_${
            sequenceNumber.toString().padStart(Context.numberOfDigitsInSequence, '0')
        }.$fileExtension"
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyyMMdd")
        return formatter.format(date)
    }

    fun loadSidecars() {
        val baseFile = fileName.substringBeforeLast('.', "")
        val directory = Paths.get(Context.workingDirectory.absolutePath)

        Files.newDirectoryStream(directory, "$baseFile*").use { stream ->
            for (path in stream) {
                if (path.fileName.toString().startsWith(baseFile)) {
                    sidecars.add(path.fileName.toString())
                }
            }
        }
    }

    fun doActualRename() {
        if (renamed) {
            throw AlreadyRenamedException(this)
        }
        renameFile(fileName, newFileName)
        if (sidecars.isNotEmpty()) {
            sidecars.collection.onEach { it.rename(newFileName) }
        }
        renamed = true;
    }

}
