import java.awt.FlowLayout
import java.awt.Image
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

internal class GalleryView : JPanel() {
    // Method to get the gallery panel
    //  for the gallery view
    // Initialize the gallery panel with a flow layout
    var galleryPanel: JPanel = JPanel(FlowLayout())

    // Method to populate the gallery view with images
    fun populateGalleryView(directory: File) {
        galleryPanel.removeAll() // Clear the panel
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (isImageFile(file)) {
                    try {
                        // Create a thumbnail for the image
                        val img = ImageIO.read(file)
                        if (img != null) {
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
        }
        revalidate()
        repaint()
    }

    // Check if the file is an image based on its extension
    fun isImageFile(file: File): Boolean {
        val imageExtensions = arrayOf("png", "jpg", "jpeg", "gif", "bmp")
        val fileName = file.name.lowercase(Locale.getDefault())
        for (ext in imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true
            }
        }
        return false
    }
}
