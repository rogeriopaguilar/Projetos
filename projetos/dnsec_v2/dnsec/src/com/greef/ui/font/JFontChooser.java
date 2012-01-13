package com.greef.ui.font;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <code>JFontChooser</code> provides a pane of controls designed to allow
 * a user to manipulate and select a font.
 *
 * This class provides three levels of API:
 * <ol>
 * <li>A static convenience method which shows a modal font-chooser
 * dialog and returns the font selected by the user.
 * <li>A static convenience method for creating a font-chooser dialog
 * where <code>ActionListeners</code> can be specified to be invoked when
 * the user presses one of the dialog buttons.
 * <li>The ability to create instances of <code>JFontChooser</code> panes
 * directly (within any container). <code>PropertyChange</code> listeners
 * can be added to detect when the current "font" property changes.
 * </ol>
 * <p>
 *
 * @author Adrian BER
 */
public class JFontChooser extends JComponent {

    /** The list of possible font sizes. */
    private static final Integer SIZES[] = {  new Integer(8), new Integer(9),
        new Integer(10), new Integer(11), new Integer(12), new Integer(13),
        new Integer(14), new Integer(16), new Integer(18), new Integer(20),
        new Integer(24), new Integer(26), new Integer(28), new Integer(32),
        new Integer(36), new Integer(40), new Integer(48), new Integer(56),
        new Integer(64), new Integer(72)};

    /** The list of possible fonts. */
    private static final String[] FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getAvailableFontFamilyNames();


    private FontSelectionModel selectionModel;

    private JList fontList;

    private JList sizeList;

    private JCheckBox boldCheckBox;

    private JCheckBox italicCheckBox;

    private JLabel previewLabel;

    /** The preview text, if null the font name will be the preview text. */
    private String previewText;

    /** Listener used to update the font of the selection model. */
    private SelectionUpdater selectionUpdater = new SelectionUpdater();

    /** Listener used to update the font in the components. This should be registered
     * with the selection model. */
    private LabelUpdater labelUpdater = new LabelUpdater();

    /** True if the components are being updated and no event should be generated. */
    private boolean updatingComponents = false;

    /** Listener class used to update the font in the components. This should be registered
      * with the selection model. */
     private class LabelUpdater implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            updateComponents();
        }

    }

    /** Listener class used to update the font of the preview label. */
    private class SelectionUpdater implements ChangeListener, ListSelectionListener {

        public void stateChanged(ChangeEvent e) {
            if (!updatingComponents) {
                setFont(buildFont());
            }
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!updatingComponents) {
                setFont(buildFont());
            }
        }
    }

    /**
     * Shows a modal font-chooser dialog and blocks until the
     * dialog is hidden.  If the user presses the "OK" button, then
     * this method hides/disposes the dialog and returns the selected color.
     * If the user presses the "Cancel" button or closes the dialog without
     * pressing "OK", then this method hides/disposes the dialog and returns
     * <code>null</code>.
     *
     * @param component    the parent <code>Component</code> for the dialog
     * @param title        the String containing the dialog's title
     * @param initialFont the initial Font set when the font-chooser is shown
     * @return the selected font or <code>null</code> if the user opted out
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static Font showDialog(Component component, String title, Font initialFont) {

        final JFontChooser pane = new JFontChooser(initialFont);

        FontTracker ok = new FontTracker(pane);
        JDialog dialog = createDialog(component, title, true, pane, ok, null);
        dialog.addWindowListener(new FontChooserDialog.Closer());
        dialog.addComponentListener(new FontChooserDialog.DisposeOnClose());

        dialog.setVisible(true); // blocks until user brings dialog down...

        return ok.getFont();
    }


    /**
     * Creates and returns a new dialog containing the specified
     * <code>ColorChooser</code> pane along with "OK", "Cancel", and "Reset"
     * buttons. If the "OK" or "Cancel" buttons are pressed, the dialog is
     * automatically hidden (but not disposed).  If the "Reset"
     * button is pressed, the color-chooser's color will be reset to the
     * font which was set the last time <code>show</code> was invoked on the
     * dialog and the dialog will remain showing.
     *
     * @param c              the parent component for the dialog
     * @param title          the title for the dialog
     * @param modal          a boolean. When true, the remainder of the program
     *                       is inactive until the dialog is closed.
     * @param chooserPane    the font-chooser to be placed inside the dialog
     * @param okListener     the ActionListener invoked when "OK" is pressed
     * @param cancelListener the ActionListener invoked when "Cancel" is pressed
     * @return a new dialog containing the font-chooser pane
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static JDialog createDialog(Component c, String title, boolean modal,
        JFontChooser chooserPane, ActionListener okListener,
        ActionListener cancelListener) {

        return new FontChooserDialog(c, title, modal, chooserPane,
                okListener, cancelListener);
    }

    /**
     * Creates a color chooser pane with an initial font which is the same font
     * as the default font for labels.
     */
    public JFontChooser() {
        this(new DefaultFontSelectionModel());
    }

    /**
     * Creates a font chooser pane with the specified initial font.
     *
     * @param initialFont the initial font set in the chooser
     */
    public JFontChooser(Font initialFont) {
        this(new DefaultFontSelectionModel(initialFont));
    }

    /**
     * Creates a font chooser pane with the specified
     * <code>FontSelectionModel</code>.
     *
     * @param model the font selection model used by this component
     */
    public JFontChooser(FontSelectionModel model) {
	    selectionModel = model;
        init(model.getSelectedFont());
        selectionModel.addChangeListener(labelUpdater);
    }

    private void init(Font font) {
        setLayout(new GridBagLayout());

        Insets ins = new Insets(2, 2, 2, 2);

        fontList = new JList(FONTS);
        fontList.setVisibleRowCount(10);
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(fontList), new GridBagConstraints(0, 0, 1, 1, 2, 2,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                ins, 0, 0));

        sizeList = new JList(SIZES);
        sizeList.setVisibleRowCount(10);
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(sizeList), new GridBagConstraints(1, 0, 1, 1, 1, 2,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                ins, 0, 0));

        boldCheckBox = new JCheckBox("Bold");
        add(boldCheckBox, new GridBagConstraints(0, 1, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                ins, 0, 0));

        italicCheckBox = new JCheckBox("Italic");
        add(italicCheckBox, new GridBagConstraints(0, 2, 2, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                ins, 0, 0));

        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(JLabel.CENTER);
        previewLabel.setVerticalAlignment(JLabel.CENTER);
        add(new JScrollPane(previewLabel), new GridBagConstraints(0, 3, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                ins, 0, 0));

        setFont(font == null ? previewLabel.getFont() : font);

        fontList.addListSelectionListener(selectionUpdater);
        sizeList.addListSelectionListener(selectionUpdater);
        boldCheckBox.addChangeListener(selectionUpdater);
        italicCheckBox.addChangeListener(selectionUpdater);
    }

    private Font buildFont() {
//        Font labelFont = previewLabel.getFont();

        String fontName = (String)fontList.getSelectedValue();
        if (fontName == null) {
            return null;
//            fontName = labelFont.getName();
        }
        Integer sizeInt = (Integer)sizeList.getSelectedValue();
        int size;
        if (sizeInt == null) {
//            size = labelFont.getSize();
            return null;
        }
        size = sizeInt.intValue();
        // create the font
        return new Font(fontName,
                (italicCheckBox.isSelected() ? Font.ITALIC : Font.PLAIN)
                | (boldCheckBox.isSelected() ? Font.BOLD : Font.PLAIN),
                size);
    }

    /** Updates the font in the preview component according to the selected values. */
    private void updateComponents() {
        updatingComponents = true;

        Font font = getFont();

        fontList.setSelectedValue(font.getName(), true);
        sizeList.setSelectedValue(new Integer(font.getSize()), true);
        boldCheckBox.setSelected(font.isBold());
        italicCheckBox.setSelected(font.isItalic());

        if (previewText == null) {
            previewLabel.setText(font.getName());
        }

        // set the font and fire a property change
        Font oldValue = previewLabel.getFont();
        previewLabel.setFont(font);
        firePropertyChange("font", oldValue, font);

        updatingComponents = false;
    }

    /**
     * Returns the data model that handles font selections.
     *
     * @return a FontSelectionModel object
     */
    public FontSelectionModel getSelectionModel() {
        return selectionModel;
    }


    /**
     * Set the model containing the selected font.
     *
     * @param newModel   the new FontSelectionModel object
     */
    public void setSelectionModel(FontSelectionModel newModel ) {
        FontSelectionModel oldModel = selectionModel;
	    selectionModel = newModel;
        oldModel.removeChangeListener(labelUpdater);
        newModel.addChangeListener(labelUpdater);
	    firePropertyChange("selectionModel", oldModel, newModel);
    }

    /**
     * Gets the current font value from the font chooser.
     *
     * @return the current font value of the font chooser
     */
    public Font getFont() {
        return selectionModel.getSelectedFont();
    }

    /**
     * Sets the current font of the font chooser to the specified font.
     * The <code>ColorSelectionModel</code> will fire a <code>ChangeEvent</code>
     * @param font the font to be set in the font chooser
     * @see JComponent#addPropertyChangeListener
     */
    public void setFont(Font font) {
        selectionModel.setSelectedFont(font);
    }

    /** Returns the preview text displayed in the preview component.
     * @return the preview text, if null the font name will be displayed
     */
    public String getPreviewText() {
        return previewText;
    }

    /** Sets the preview text displayed in the preview component.
     * @param previewText the preview text, if null the font name will be displayed
     */
    public void setPreviewText(String previewText) {
        this.previewText = previewText;
        previewLabel.setText(previewText);
    }

}


/*
 * Class which builds a font chooser dialog consisting of
 * a JFontChooser with "Ok", "Cancel", and "Reset" buttons.
 *
 * Note: This needs to be fixed to deal with localization!
 */
class FontChooserDialog extends JDialog {
    private Font initialFont;
    private JFontChooser chooserPane;

    public FontChooserDialog(Component c, String title, boolean modal,
              JFontChooser chooserPane,
              ActionListener okListener, ActionListener cancelListener) {
        super(JOptionPane.getFrameForComponent(c), title, modal);
        //setResizable(false);

        String okString = UIManager.getString("ColorChooser.okText");
        String cancelString = UIManager.getString("ColorChooser.cancelText");
        String resetString = UIManager.getString("ColorChooser.resetText");

        /*
         * Create Lower button panel
         */
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton(okString);
        getRootPane().setDefaultButton(okButton);
        okButton.setActionCommand("OK");
        if (okListener != null) {
            okButton.addActionListener(okListener);
        }
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPane.add(okButton);

        JButton cancelButton = new JButton(cancelString);

        // The following few lines are used to register esc to close the dialog
        Action cancelKeyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // todo make it in 1.3
//                ActionListener[] listeners
//                        = ((AbstractButton) e.getSource()).getActionListeners();
//                for (int i = 0; i < listeners.length; i++) {
//                    listeners[i].actionPerformed(e);
//                }
            }
        };
        KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE);
        InputMap inputMap = cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = cancelButton.getActionMap();
        if (inputMap != null && actionMap != null) {
            inputMap.put(cancelKeyStroke, "cancel");
            actionMap.put("cancel", cancelKeyAction);
        }
        // end esc handling

        cancelButton.setActionCommand("cancel");
        if (cancelListener != null) {
            cancelButton.addActionListener(cancelListener);
        }
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPane.add(cancelButton);

        JButton resetButton = new JButton(resetString);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        int mnemonic = UIManager.getInt("ColorChooser.resetMnemonic");
        if (mnemonic != -1) {
            resetButton.setMnemonic(mnemonic);
        }
        buttonPane.add(resetButton);


        // initialiase the content pane
        this.chooserPane = chooserPane;

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(chooserPane, BorderLayout.CENTER);

        contentPane.add(buttonPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(c);
    }

    public void setVisible(boolean visible) {
        if (visible)
            initialFont = chooserPane.getFont();
        super.setVisible(visible);
    }

    public void reset() {
        chooserPane.setFont(initialFont);
    }

    static class Closer extends WindowAdapter implements Serializable {
        public void windowClosing(WindowEvent e) {
            Window w = e.getWindow();
            w.setVisible(false);
        }
    }

    static class DisposeOnClose extends ComponentAdapter implements Serializable {
        public void componentHidden(ComponentEvent e) {
            Window w = (Window) e.getComponent();
            w.dispose();
        }
    }

}

class FontTracker implements ActionListener, Serializable {
    JFontChooser chooser;
    Font color;

    public FontTracker(JFontChooser c) {
        chooser = c;
    }

    public void actionPerformed(ActionEvent e) {
        color = chooser.getFont();
    }

    public Font getFont() {
        return color;
    }
}
