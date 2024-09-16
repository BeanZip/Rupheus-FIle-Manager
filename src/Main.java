import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

class Main{
    static class Window {

        JFrame frame = new JFrame("Rupheus File Manager");
        public void InitWindow(int ScreenWidth,int ScreenHeight){
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenWidth,ScreenHeight);
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
        public int x;
        public int y;
        JMenuBar menuBar = new JMenuBar();
        JMenu Help = new JMenu("Help");
        JMenu Options = new JMenu("Options");

        JMenu theme = new JMenu("Theme");

        JMenuItem tutorial = new JMenuItem("How This Works");
        JMenuItem GitHub = new JMenuItem("GitHub");

        JMenuItem Dark = new JMenuItem("Dark Mode");
        JMenuItem System = new JMenuItem("System Default");
        JMenuItem light = new JMenuItem("Light Mode");


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

            System.addActionListener(e ->{
                FlatIntelliJLaf.setup();
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
            theme.add(System);
            frame.setJMenuBar(menuBar);
        }
    }


    public static void main(String[] args){
        Menu m1 = new Menu();

        m1.x = 600;
        m1.y = m1.x/2;
        m1.InitWindow(m1.x, m1.y);
        m1.menu();
        m1.interactive();
        m1.Visible(true);
    }
}
