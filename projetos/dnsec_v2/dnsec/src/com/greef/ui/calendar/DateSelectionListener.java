package com.greef.ui.calendar;

/**
 * Defines an object which listens for <code>{@link DateSelectionEvent}</code>s.
 * @author Adrian BER
 */
public interface DateSelectionListener {

    /** Invoked when the target of the listener has changed its selection. */
    void changed(DateSelectionEvent event);
}
