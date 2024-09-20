
    import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

    public class GalleryView extends JPanel {
        private final JPanel galleryPanel = new JPanel(new FlowLayout()); // Initialize with FlowLayout

        public GalleryView() {
            add(galleryPanel); // Add the galleryPanel to the parent panel
        }

        // Method to populate the gallery with image files
        public void populateGalleryView(File directory) {
            galleryPanel.removeAll(); // Clear previous content
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (isImageFile(file)) {
                        try {
                            Image img = ImageIO.read(file);
                            if (img != null) {
                                // Create a scaled icon from the image
                                ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                                JLabel label = new JLabel(icon);
                                label.setText(file.getName());
                                label.setHorizontalTextPosition(JLabel.CENTER);
                                label.setVerticalTextPosition(JLabel.BOTTOM);
                                galleryPanel.add(label);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            revalidate();
            repaint();
        }

        // Method to check if the file is an image
        private boolean isImageFile(File file) {
            String[] imageExtensions = {"png", "jpg", "jpeg", "gif", "bmp"};
            String fileName = file.getName().toLowerCase();
            for (String extension : imageExtensions) {
                if (fileName.endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

