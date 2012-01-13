package com.greef.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * This is a panel from where you can choose a color from the look'n'feel.
 * This panel will be integrated in a JColorChooser component.
 * @author Adrian Ber
 */
public class LnFColorsChooserPanel extends AbstractColorChooserPanel {

    /** The name of this color chooser panel; will appear as the tab name
     *  in the color chooser
     */
    private static final String TITLE = "Look'n'Feel";

    /** The name displayed for a color without a look'n'feel name */
    private static final String CUSTOM = "<Custom>";

    /** The combo box filled with the look'n'feel color names */
    private JComboBox colorsComboBox = new JComboBox();

    private Map uiColors = new HashMap();

    /** Creates a new color chooser panel. */
    public LnFColorsChooserPanel() {
        buildChooser();
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    private Object getUIColorKey(Color color) {
        return uiColors.get(color);
    }

    public void updateChooser() {
        if (parent != null) {
            Object x = getUIColorKey(getColorFromModel());
            if (x == null) {
                x = CUSTOM;
            }
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
        UIDefaults defaults = UIManager.getDefaults();
        TreeSet colorNames = new TreeSet();
        for (Enumeration e = defaults.keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object value = defaults.get(key);
            if (value instanceof Color) {
                uiColors.put(value, key);
                colorNames.add(key.toString());
            }
        }
        for (Iterator i = colorNames.iterator(); i.hasNext();) {
            colorsComboBox.addItem(i.next());
        }
        colorsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (parent == null) return;
                Object name = colorsComboBox.getSelectedItem();
                if ((name != null) && (!name.toString().equals(CUSTOM))) {
                    parent.setColor(UIManager.getDefaults().getColor(name));
                }
            }
        });
        add(new JLabel("Look'n'feel color:"), BorderLayout.WEST);
        add(colorsComboBox, BorderLayout.CENTER);
    }

    /** the color chooser in which is included this panel. */
    private JColorChooser parent = null;

    public void installChooserPanel(JColorChooser enclosingChooser) {
        parent = enclosingChooser;
        super.installChooserPanel(enclosingChooser);
    }

}