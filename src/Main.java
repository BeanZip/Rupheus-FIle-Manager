import javax.swing.*;
import java.awt.*;
import java.net.URI;

class Main{
    static class Window {

        JFrame frame = new JFrame("File Manager CL12");
        boolean x = true;
        public void InitWindow(int ScreenWidth,int ScreenHeight){
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenWidth,ScreenHeight);
        }

        public void Visible(){
            frame.setVisible(x);
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

        JMenu adjust = new JMenu("Adjust");
        JMenu theme = new JMenu("Theme");

        JMenuItem tutorial = new JMenuItem("How This Works");
        JMenuItem GitHub = new JMenuItem("GitHub");

        JMenuItem Dark = new JMenuItem("Dark Mode");
        JMenuItem System = new JMenuItem("System Default");
        JMenuItem light = new JMenuItem("Light Mode");


        JMenuItem large = new JMenuItem("Large");
        JMenuItem medium = new JMenuItem("Default");
        JMenuItem small = new JMenuItem("Small");

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
                //insert flatlaf and dark module
            });

            light.addActionListener(e ->{
                //insert flatlaf and light module
            });

            System.addActionListener(e ->{
                //insert flatlaf intelliJ
            });
        }
    }
}
