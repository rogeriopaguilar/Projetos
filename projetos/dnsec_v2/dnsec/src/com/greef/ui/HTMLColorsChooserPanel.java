package com.greef.ui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * This is a panel from where you can choose a color based on it's HTML name.
 * This panel was designed to be integrated in JColorChooser component.
 * @author Adrian Ber
 * @see HTMLColors
 */
public class HTMLColorsChooserPanel extends AbstractColorChooserPanel {

    /** The name of this color chooser panel; will appear as the tab name
     *  in the color chooser
     */
    private static final String TITLE = "HTML Names";
    /** The name displayed for a color without a HTML name */
    private static final String CUSTOM = "<Custom>";

    /** The combo box filled with the HTML color names */
    private JComboBox colorsComboBox = new JComboBox();

    /** Creates a new color chooser panel. */
    public HTMLColorsChooserPanel() {
        buildChooser();
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public void updateChooser() {
        if (parent != null) {
            String x = HTMLColors.getName(getColorFromModel());
            if (x == null) x = CUSTOM;
            colorsComboBox.setSelectedItem(x);
        }
    }

    public String getDisplayName() {
        return TITLE;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }

    /** Initializes this color chooser components. */
    protected void buildChooser() {
        setLayout(new BorderLayout());
        colorsComboBox.addItem(CUSTOM);
        for (Enumeration e = HTMLColors.colors(); e.hasMoreElements();) {
            colorsComboBox.addItem(e.nextElement());
        }
        colorsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (parent == null) return;
                Object name = colorsComboBox.getSelectedItem();
                if ((name != null) && (!name.toString().equals(CUSTOM))) {
                    parent.setColor(HTMLColors.getColor(name.toString()));
                }
            }
        });
        add(new JLabel("HTML Color:"), BorderLayout.WEST);
        add(colorsComboBox, BorderLayout.CENTER);
    }

    /** the color chooser in which is included this panel. */
    private JColorChooser parent = null;

    public void installChooserPanel(JColorChooser enclosingChooser) {
        parent = enclosingChooser;
        super.installChooserPanel(enclosingChooser);
    }

}