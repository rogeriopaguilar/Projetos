package com.greef.ui.font;

import java.awt.Font;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A generic implementation of <code>{@link FontSelectionModel}</code>.
 *
 * @author Adrian BER
 */
public class DefaultFontSelectionModel implements FontSelectionModel {

    /** The default selected font. */
    private static final Font DEFAULT_INITIAL_FONT = new Font("Dialog", Font.PLAIN, 12);
    
    /** The selected font. */
    private Font selectedFont;

    /** The change listeners notified by a change in this model. */
    private Collection listeners = new HashSet();

    /**
     * Creates a <code>DefaultFontSelectionModel</code> with the
     * current font set to <code>Dialog, 12</code>.  This is
     * the default constructor.
     */
    public DefaultFontSelectionModel() {
        this(DEFAULT_INITIAL_FONT);
    }

    /**
     * Creates a <code>DefaultFontSelectionModel</code> with the
     * current font set to <code>font</code>, which should be
     * non-<code>null</code>.  Note that setting the font to
     * <code>null</code> is undefined and may have unpredictable
     * results.
     *
     * @param selectedFont the new <code>Font</code>
     */
    public DefaultFontSelectionModel(Font selectedFont) {
        if (selectedFont == null) {
            selectedFont = DEFAULT_INITIAL_FONT;
        }
        this.selectedFont = selectedFont;
    }

    public Font getSelectedFont() {
        return selectedFont;
    }

    public void setSelectedFont(Font selectedFont) {
        if (selectedFont != null) {
            this.selectedFont = selectedFont;
            fireChangeListeners();
        }
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    /** Fires the listeners registered with this model. */
    protected void fireChangeListeners() {
        ChangeEvent ev = new ChangeEvent(this);
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
            ChangeListener listener = (ChangeListener) iterator.next();
            listener.stateChanged(ev);
        }
    }
}
