import me.mintdev.models.Image
import me.mintdev.models.ImageCollection
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ImageCollectionTest {

    @Test
    fun testHasDuplicateFilenames_NoDuplicates() {
        val images = listOf(
            Image("file1.png", newFileName = "file1.png"),
            Image("file2.png", newFileName = "file2.png"),
            Image("file3.png", newFileName = "file3.png")
        )

        val collection = ImageCollection.fromListOfImages(images)

        val result = collection.hasDuplicateFilenames()
        assertFalse(result, "must return false without duplicates")
    }

    @Test
    fun testHasDuplicateFilenames_WithDuplicates() {
        val images = listOf(
            Image("file1.png", newFileName = "file1.png"),
            Image("file2.png", newFileName = "file2.png"),
            Image("file3.png", newFileName = "file1.png")
        )

        val collection = ImageCollection.fromListOfImages(images)

        val result = collection.hasDuplicateFilenames()
        assertTrue(result, "must return true with duplicates")

    }

}
