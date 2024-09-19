import java.awt.FlowLayout
import java.awt.Image
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

class GalleryView : JPanel() {
    private val galleryPanel = JPanel(FlowLayout()) // Initialize with FlowLayout

    init {
        add(galleryPanel) // Add the galleryPanel to the parent panel
    }

    // Method to populate the gallery with image files
    fun populateGalleryView(directory: File) {
        galleryPanel.removeAll() // Clear previous content
        val files = directory.listFiles()

        files?.forEach { file ->
            if (isImageFile(file)) {
                try {
                    val img = ImageIO.read(file)
                    if (img != null) {
                        // Create a scaled icon from the image
                        val icon = ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH))
                        val label = JLabel(icon)
                        label.text = file.name
                        label.horizontalTextPosition = JLabel.CENTER
                        label.verticalTextPosition = JLabel.BOTTOM
                        galleryPanel.add(label)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        revalidate()
        repaint()
    }

    // Method to check if the file is an image
    private fun isImageFile(file: File): Boolean {
        val imageExtensions = arrayOf("png", "jpg", "jpeg", "gif", "bmp")
        val fileName = file.name.toLowerCase()
        return imageExtensions.any { fileName.endsWith(it) }
    }
}

