import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkMediumIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

class Main {
    static class Window extends JFrame {
        JFrame frame = new JFrame("Rupheus File Manager");

        public void InitWindow(int ScreenWidth, int ScreenHeight) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenWidth, ScreenHeight);
            frame.getContentPane().setLayout(new BorderLayout());
        }

        public void Visible(boolean Displaying) {
            frame.setVisible(Displaying);
            if (!frame.isVisible()) {
                System.out.print("Error Could not Initialize");
            } else {
                JOptionPane.showMessageDialog(frame, "Window Successfully Deployed!");
            }
        }
    }

    static class Menu extends Window {
        JMenuBar menuBar = new JMenuBar();
        JMenu Help = new JMenu("Help");
        JMenu Options = new JMenu("Options");
        JMenu view = new JMenu("View Mode");

        JMenu theme = new JMenu("Theme");

        JMenuItem tree = new JMenuItem("Tree View");
        JMenuItem gallery = new JMenuItem("Gallery View");

        JMenu og = new JMenu("Default Themes");
        JMenu git = new JMenu("Github Colors");
        JMenu Gruv = new JMenu("Gruvbox Themes");
        JMenu atom = new JMenu("Atom Theme");

        JMenuItem tutorial = new JMenuItem("How This Works");
        JMenuItem GitHub = new JMenuItem("GitHub");
        JMenuItem Dark = new JMenuItem("Dark Mode");

        JMenuItem GruvSoft = new JMenuItem("Gruvbox Soft");
        JMenuItem GruvHard = new JMenuItem("Gruvbox Hard");
        JMenuItem GruvMid = new JMenuItem("Gruvbox Medium");

        JMenuItem atomLight = new JMenuItem("Atom Light");
        JMenuItem atomDark = new JMenuItem("Atom Dark");

        JMenuItem GitL = new JMenuItem("Github Light");
        JMenuItem GitD = new JMenuItem("Github Dark");

        JMenuItem light = new JMenuItem("Light Mode");
        JMenuItem night = new JMenuItem("Night Owl");

        public void interactive() {
            tutorial.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Choose A File and How To Interact With it.");
            });

            GitHub.addActionListener(e -> {
                try {
                    URI uri = new URI("https://github.com/BeanZip");
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(uri);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Could Not Be Initialized");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            Dark.addActionListener(e -> {
                FlatDarkLaf.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            light.addActionListener(e -> {
                FlatLightLaf.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            GruvSoft.addActionListener(e -> {
                FlatGruvboxDarkSoftIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            GruvHard.addActionListener(e -> {
                FlatGruvboxDarkHardIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            GruvMid.addActionListener(e -> {
                FlatGruvboxDarkMediumIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            night.addActionListener(e -> {
                FlatNightOwlIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            GitL.addActionListener(e -> {
                FlatGitHubIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            GitD.addActionListener(e -> {
                FlatGitHubDarkIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            atomLight.addActionListener(e -> {
                FlatAtomOneLightIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            atomDark.addActionListener(e -> {
                FlatAtomOneDarkIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });
        }

        public void menu() {
            menuBar.add(Help);
            menuBar.add(Options);
            Help.add(tutorial);
            Help.add(GitHub);
            Options.add(theme);
            Options.add(view);
            view.add(tree);
            view.add(gallery);
            theme.add(og);
            theme.add(git);
            theme.add(atom);

            git.add(GitD);
            git.add(GitL);

            atom.add(atomDark);
            atom.add(atomLight);

            og.add(light);
            og.add(Dark);
            theme.add(Gruv);
            Gruv.add(GruvHard);
            Gruv.add(GruvMid);
            Gruv.add(GruvSoft);
            theme.add(night);
            frame.setJMenuBar(menuBar);
        }
    }

    static class FileManager extends Menu implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {

        }

        // GalleryView Class embedded within FileManager
        static class GalleryView extends JPanel {
            private final JPanel galleryPanel = new JPanel(new FlowLayout());

            public GalleryView() {
                add(galleryPanel);
            }

            // Populate the gallery with image files from a directory
            public void populateGalleryView(File directory) {
                galleryPanel.removeAll();
                File[] files = directory.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (isImageFile(file)) {
                            try {
                                Image img = ImageIO.read(file);
                                if (img != null) {
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

        private GalleryView galleryView = new GalleryView();
        private JTree fileTree;
        private DefaultMutableTreeNode rootNode;
        private JScrollPane treeScrollPane;
        String[] columnNames = {"Name", "Size", "Last Modified"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable fileTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(fileTable);

        public FileManager() {
            super();
        }

        public void interactivegallery() {
            gallery.addActionListener(e -> switchToGalleryView());
            tree.addActionListener(e -> switchToTreeView());
        }

        void fileworks() {
            rootNode = new DefaultMutableTreeNode(new FileNode(new File(File.listRoots()[0].getAbsolutePath())));
            fileTree = new JTree(buildFileTree(rootNode, new File(File.listRoots()[0].getAbsolutePath())));
            fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            fileTree.addTreeSelectionListener(e -> {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    FileNode selectedFileNode = (FileNode) selectedNode.getUserObject();
                    populateFileTable(selectedFileNode.getFile());
                }
            });

            treeScrollPane = new JScrollPane(fileTree);
        }

        private DefaultMutableTreeNode buildFileTree(DefaultMutableTreeNode node, File file) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(f));
                    if (f.isDirectory()) {
                        buildFileTree(childNode, f);
                    }
                    node.add(childNode);
                }
            }
            return node;
        }

        private void populateFileTable(File directory) {
            File[] files = directory.listFiles();
            if (files != null) {
                tableModel.setRowCount(0);
                for (File file : files) {
                    String name = file.getName();
                    long size = file.length();
                    String lastModified = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(file.lastModified()));
                    tableModel.addRow(new Object[]{name, size, lastModified});
                }
            }
        }

        public void switchToGalleryView() {
            // Choose a directory manually for now or use a file chooser for testing
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();

                if (selectedDirectory != null && selectedDirectory.isDirectory()) {
                    // Populate the gallery view with image files from the selected directory
                    galleryView.populateGalleryView(selectedDirectory);

                    frame.getContentPane().removeAll(); // Clear the frame
                    frame.add(galleryView, BorderLayout.CENTER); // Add gallery view
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Directory Selected!");
                }
            }
        }


        public void switchToTreeView() {
            frame.getContentPane().removeAll();
            frame.add(treeScrollPane, BorderLayout.WEST);
            frame.add(tableScrollPane, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        FileManager manager = new FileManager();
        manager.InitWindow(800, 600);
        manager.fileworks();
        manager.interactive();
        manager.menu();
        manager.interactivegallery();
        manager.switchToTreeView(); // Start with Tree View
        manager.Visible(true);
    }
}

class FileNode {
    private final File file;

    public FileNode(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName().isEmpty() ? file.getAbsolutePath() : file.getName();
    }
}
