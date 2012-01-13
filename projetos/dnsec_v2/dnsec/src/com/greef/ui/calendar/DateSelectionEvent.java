package com.greef.ui.calendar;

import java.util.Date;
import java.util.EventObject;

/**
 * ChangeEvent is used to notify interested parties that
 * state has changed in the <code>{@link DateSelectionModel}</code>.
 * @author Adrian BER
 */
public class DateSelectionEvent extends EventObject {

    /** The starting date of the selection. */
    private Date from;

    /** The ending date of the selection. */
    private Date to;

    /** Creates a mew date selection event with a source, a starting and ending date. */
    public DateSelectionEvent(Object source, Date from, Date to) {
        super(source);
        this.from = from;
        this.to = to;
    }

    /** @return the starting date. */
    public Date getFrom() {
        return from;
    }

    /** @return the ending date. */
    public Date getTo() {
        return to;
    }

    public String toString() {
        return super.toString() + "[" + from + " -> " + to + "]";
    }
}
