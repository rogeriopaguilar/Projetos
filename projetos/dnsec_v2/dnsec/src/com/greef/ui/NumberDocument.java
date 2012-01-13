package com.greef.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A text document to be used in text components that accepts only numbers.
 * @author Adrian BER
 */
public class NumberDocument extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        try {
            Integer.parseInt(str);
            super.insertString(offs, str, a);
        } catch(NumberFormatException exc) {
            // just ignore if not a number
        }
    }

}
