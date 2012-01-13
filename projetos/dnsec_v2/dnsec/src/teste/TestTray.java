package teste;

import javax.swing.JMenuItem;

public class TestTray {
    public static JMenuItem quit;
    
   /* public TestTray() {
        JPopupMenu menu = new JPopupMenu("Tray Icon Menu");
        menu.add(new JMenuItem("Test Item"));
        menu.addSeparator();
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }});
        menu.add(quitItem);
        
        // Resource file "duke.gif" must exist at the same directory
        // as this class file.
        ImageIcon icon = new ImageIcon("duke.gif");
        TrayIcon ti = new TrayIcon(icon, "JDIC Tray Icon API Test", menu);

        // Action listener for left click.
        ti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                    "JDIC Tray Icon API Test!", "About",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
               
        SystemTray tray = SystemTray.getDefaultSystemTray();
        tray.addTrayIcon(ti);
    }*/

    public static void main(String[] args) {
        new TestTray();
    }   
}