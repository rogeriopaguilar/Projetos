package com.greef.ui.calendar;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.greef.util.Interval;
import com.greef.util.Intervals;

/**
 * A default implementation for {@link DateSelectionModel} using intervals.
 * @author Adrian BER
 */
public class DefaultDateSelectionModel implements DateSelectionModel {

    /** The listeners to be fired every time a change is made to this selection. */
    private Collection listeners = new HashSet();

    /** The intervals selected in this selection. */
    private Intervals intervals = new Intervals();

    public Date getMinSelectedDate(Date from, Date to) {
        List l = intervals.intersect(new Interval(from, to)).getIntervals();
        return l.size() <= 0 ? null : (Date)((Interval)l.get(0)).getFrom();
    }

    public Date getMaxSelectedDate(Date from, Date to) {
        List l = intervals.intersect(new Interval(from, to)).getIntervals();
        return l.size() <= 0 ? null : (Date)((Interval)l.get(l.size() - 1)).getTo();
    }

    public boolean isDateSelected(Date date) {
        return intervals.contains(date);
    }

    public void setSelectionInterval(Date from, Date to) {
        System.out.println("set " + from + " -> " + to);
        intervals.clear();
        intervals.add(new Interval(from, to));
    }

    public void addSelectedInterval(Date from, Date to) {
        System.out.println("add " + from + " -> " + to);
        intervals.add(new Interval(from, to));
    }

    public void removeSelectedInterval(Date from, Date to) {
        System.out.println("del " + from + " -> " + to);
        intervals.remove(new Interval(from, to));
    }

    public void removeAllSelectedIntervals() {
        System.out.println("clear");        
        intervals.clear();
    }

    public void addDateSelectionListener(DateSelectionListener listener) {
        listeners.add(listener);
    }

    public void removeDateSelectionListener(DateSelectionListener listener) {
        listeners.remove(listener);
    }

    /** Fires the listeners registered with this selection model to inform them
     * that a change has been made in the given interval.
     * @param from the beggining date for the changed interval
     * @param to the ending date for the changed interval
     */
    protected void fireListeners(Date from, Date to) {
        DateSelectionEvent ev = new DateSelectionEvent(this, from, to);
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
            DateSelectionListener listener = (DateSelectionListener) iterator.next();
            listener.changed(ev);
        }
    }
}
