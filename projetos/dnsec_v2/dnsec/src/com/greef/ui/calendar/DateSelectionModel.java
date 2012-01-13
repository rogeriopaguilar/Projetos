package com.greef.ui.calendar;

import java.util.Date;

/**
 * A model that supports selecting <code>Date</code>'s or intervals.
 *
 * @author Adrian BER
 *
 * @see java.util.Date
 */
public interface DateSelectionModel {

    /** Returns if the given date is selected in this model. */
    boolean isDateSelected(Date date);

    /** Returns the minimmun selected date in the given interval.
     * @param from the starting date
     * @param to the ending date
     */
    Date getMinSelectedDate(Date from, Date to);

    /** Returns the maximmun selected date in the given interval.
     * @param from the starting date
     * @param to the ending date
     */
    Date getMaxSelectedDate(Date from, Date to);

    /** Removes an interval to the selected ones. */
    void setSelectionInterval(Date from, Date to);

    /** Removes an interval to the selected ones. */
    void addSelectedInterval(Date from, Date to);

    /** Removes an interval to the selected ones. */
    void removeSelectedInterval(Date from, Date to);

    /** Removes all selection intervals. */
    void removeAllSelectedIntervals();

    /**
     * Adds <code>listener</code> as a listener to changes in the model.
     * @param listener the <code>DateSelectionListener</code> to be added
     */
    void addDateSelectionListener(DateSelectionListener listener);

    /**
     * Removes <code>listener</code> as a listener to changes in the model.
     * @param listener the <code>DateSelectionListener</code> to be removed
     */
    void removeDateSelectionListener(DateSelectionListener listener);

}
