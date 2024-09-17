import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

class Main{
    static class Window extends JFrame{

        JFrame frame = new JFrame("Rupheus File Manager");
        public void InitWindow(int ScreenWidth,int ScreenHeight){
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenWidth,ScreenHeight);
            frame.getContentPane().setLayout(new BorderLayout());
        }

        public void Visible(boolean Displaying){
            frame.setVisible(Displaying);
            if(!frame.isVisible()){
                System.out.print("Error Could not Initialize");
            }
            else {
                JOptionPane.showMessageDialog(frame, "Window Successfully Deployed!");
            }
        }
    }

    static class Menu extends Window{
        JMenuBar menuBar = new JMenuBar();
        JMenu Help = new JMenu("Help");
        JMenu Options = new JMenu("Options");

        JMenu theme = new JMenu("Theme");

        JMenuItem tutorial = new JMenuItem("How This Works");
        JMenuItem GitHub = new JMenuItem("GitHub");

        JMenuItem Dark = new JMenuItem("Dark Mode");
        JMenuItem Gruv = new JMenuItem("Gruvbox");
        JMenuItem light = new JMenuItem("Light Mode");
        JMenuItem night = new JMenuItem("Night Owl");

        public void interactive(){
            tutorial.addActionListener(e ->{
                JOptionPane.showMessageDialog(frame,"Choose A File and How To Interact With it.");
            });

            GitHub.addActionListener(e ->{
                try{
                    URI uri = new URI("https://github.com/BeanZip");
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)){
                        desktop.browse(uri);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"Could Not Be Initialized");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            Dark.addActionListener(e ->{
                FlatDarkLaf.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            light.addActionListener(e ->{
                FlatLightLaf.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            Gruv.addActionListener(e -> {
                FlatGruvboxDarkSoftIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });

            night.addActionListener(e ->{
                FlatNightOwlIJTheme.setup();
                SwingUtilities.updateComponentTreeUI(frame);
            });
        }

        public void menu(){
            menuBar.add(Help);
            menuBar.add(Options);
            Help.add(tutorial);
            Help.add(GitHub);
            Options.add(theme);
            theme.add(Dark);
            theme.add(light);
            theme.add(Gruv);
            theme.add(night);
            frame.setJMenuBar(menuBar);
        }
    }

    static class FileManager extends Menu{
        private JTree fileTree;
        private DefaultMutableTreeNode rootNode;
        private JScrollPane treeScrollPane;
        JFileChooser fileChooser = new JFileChooser();
        String[] columnNames = {"Name", "Size", "Last Modified"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable fileTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(fileTable);

        void fileworks(){
            rootNode = new DefaultMutableTreeNode(new FileNode(new File(File.listRoots()[0].getAbsolutePath())));
            fileTree = new JTree(buildFileTree(rootNode, new File(File.listRoots()[0].getAbsolutePath())));
            fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

            fileTree.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                    if (selectedNode != null) {
                        FileNode selectedFileNode = (FileNode) selectedNode.getUserObject();
                        populateFileTable(selectedFileNode.getFile());
                    }
                }
            });

            treeScrollPane = new JScrollPane(fileTree); // Initialize the tree scroll pane after creating fileTree
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
                tableModel.setRowCount(0); // Clear the table
                for (File file : files) {
                    String name = file.getName();
                    long size = file.length();
                    String lastModified = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(file.lastModified()));
                    tableModel.addRow(new Object[]{name, size, lastModified});
                }
            }
        }

        void drawtoframe(){
            frame.add(treeScrollPane, BorderLayout.WEST);
            frame.add(tableScrollPane, BorderLayout.CENTER);
        }
    }

    public static void main(String[] args){
        FileManager fm = new FileManager();
        fm.InitWindow(600, 600);
        fm.menu();
        fm.interactive();


        fm.fileworks();
        fm.drawtoframe();

        fm.Visible(true);
    }

    // A simple wrapper class to represent files in the JTree
    static class FileNode {
        private File file;

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
}
