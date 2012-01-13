package com.greef.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * A combobox from where you can select an available look'n'feel and install it
 * automatically on a set of root components.
 * <code>PropertyChange</code> listeners
 * can be added to detect when the current "lnf" property changes.
 * @author Adrian BER
 */
public class LnFComboBox extends JComboBox {

    /** The root components to apply the changes. */
    private Collection roots = new HashSet();

    /** Creates a new look'n'feel combobox.
     * @param root the root component on which the look'n'feel is installed.
     */
    public LnFComboBox(Component root) {
        roots.add(root);
        init();
    }

    /** Creates a new look'n'feel combobox.
     * @param roots the root components on which the look'n'feel is installed.
     */
    public LnFComboBox(Component[] roots) {
        for (int i = 0; i < roots.length; i++) {
            this.roots.add(roots[i]);
        }
        init();
    }

    private void init() {
        // get the available LnFs
        UIManager.LookAndFeelInfo[] lfs = UIManager.getInstalledLookAndFeels();
        String selLf = UIManager.getLookAndFeel().getName();
        for (int i = 0; i < lfs.length; i++) {
            addItem(lfs[i].getName());
            if (selLf.equals(lfs[i].getName())) {
                setSelectedIndex(i);
            }
        }

        // when selecting another LnF install it
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String oldValue = UIManager.getLookAndFeel().getClass().getName();
                LnFComboBox.this.installSelectedLookAndFeel();
                firePropertyChange("lnf", oldValue,
                        UIManager.getLookAndFeel().getClass().getName());
            }
        });
    }

    /**
     * Adds a root component on which the look'n'feel is installed.
     * @param root the root component
     * @return true if this component was added
     */
    public boolean addRootComponent(Component root) {
        return roots.add(root);
    }

    /**
     * Removes a root component on which the look'n'feel is installed.
     * @param root the root component
     * @return true if this component was removed
     */
    public boolean removeRootComponent(Component root) {
        return roots.remove(root);
    }

    /**
     * Sets as the system look'n'feel the selected look'n'feel.
     */
    private void installSelectedLookAndFeel() {
        if (!UIManager.getLookAndFeel().getClass().getName()
                .equals(UIManager.getInstalledLookAndFeels()[getSelectedIndex()]
                .getClassName())) {
            try {
                UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()
                        [getSelectedIndex()].getClassName());
                for (Iterator iterator = roots.iterator(); iterator.hasNext();) {
                    Component root = (Component)iterator.next();
                    SwingUtilities.updateComponentTreeUI(root);
                }
            } catch (Throwable t) {}
        }
    }

}
