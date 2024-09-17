import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManagerGUI extends JFrame {
    private JTree fileTree;
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JScrollPane treeScrollPane, tableScrollPane;

    public FileManagerGUI() {
        setTitle("File Manager");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the root node for the file tree
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileNode(new File(File.listRoots()[0].getAbsolutePath())));

        // Build the tree model
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
        treeScrollPane = new JScrollPane(fileTree);

        // Create the table to display files in the directory
        String[] columnNames = {"Name", "Size", "Last Modified"};
        tableModel = new DefaultTableModel(columnNames, 0);
        fileTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(fileTable);

        // Add components to the frame
        add(treeScrollPane, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    // Recursively build file tree nodes
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

    // Populate the file table with details from the selected directory
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileManagerGUI().setVisible(true);
            }
        });
    }

    // Custom class to represent files and directories in the tree
    static class FileNode {
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
}