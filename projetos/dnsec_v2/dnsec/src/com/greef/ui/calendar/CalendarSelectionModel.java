package com.greef.ui.calendar;

import java.util.Date;

import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Adrian BER
 */
class CalendarSelectionModel implements ListSelectionModel {

    private DateSelectionModel dateSelectionModel;
    private CalendarTableModel calendarModel;

    private int selectionMode;
    private boolean valueIsAdjusting;

    private int anchorIndex = -1;
    private int leadIndex = -1;

    protected EventListenerList listenerList = new EventListenerList();

    public CalendarSelectionModel() {
        this(new DefaultDateSelectionModel(), new CalendarTableModel());
    }

    public CalendarSelectionModel(CalendarTableModel calendarModel) {
        this(new DefaultDateSelectionModel(), calendarModel);
    }

    public CalendarSelectionModel(DefaultDateSelectionModel dateModel,
            CalendarTableModel calendarModel) {
        this.dateSelectionModel = dateModel;
        this.calendarModel = calendarModel;
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
    }

    public boolean getValueIsAdjusting() {
        return valueIsAdjusting;
    }

    public void setValueIsAdjusting(boolean valueIsAdjusting) {
        this.valueIsAdjusting = valueIsAdjusting;
    }

    public int getAnchorSelectionIndex() {
        return anchorIndex;
    }

    public int getLeadSelectionIndex() {
        return leadIndex;
    }

    public int getMaxSelectionIndex() {
        Date from = calendarModel.getCalendarAt(0, 0).getTime();
        Date to = calendarModel.getCalendarAt(calendarModel.getRowCount(),
                calendarModel.getColumnCount()).getTime();
        Date sel = dateSelectionModel.getMaxSelectedDate(from, to);
        return (sel == null ? -1 : calendarModel.getPositionFor(sel));
    }

    public int getMinSelectionIndex() {
        Date from = calendarModel.getCalendarAt(0, 0).getTime();
        Date to = calendarModel.getCalendarAt(calendarModel.getRowCount(),
                calendarModel.getColumnCount()).getTime();
        Date sel = dateSelectionModel.getMinSelectedDate(from, to);
        return (sel == null ? -1 : calendarModel.getPositionFor(sel));
    }

    public void clearSelection() {
        removeSelectionInterval(0,
                calendarModel.getRowCount() * calendarModel.getColumnCount() - 1);
    }

    public boolean isSelectionEmpty() {
        Date from = calendarModel.getCalendarAt(0, 0).getTime();
        Date to = calendarModel.getCalendarAt(calendarModel.getRowCount(),
                calendarModel.getColumnCount()).getTime();
        return dateSelectionModel.getMinSelectedDate(from, to) == null;
    }

    public void setAnchorSelectionIndex(int index) {
        anchorIndex = index;
    }

    public void setLeadSelectionIndex(int index) {
        leadIndex = index;
    }

    private void setAnchorAndLead(int index0, int index1) {
        anchorIndex = index0;
        leadIndex = index1;
    }

    public boolean isSelectedIndex(int index) {
        return dateSelectionModel.isDateSelected(calendarModel.getCalendarAt(index).getTime());
    }

    public void addSelectionInterval(int index0, int index1) {
        System.out.println("Add " + index0 + " -> " + index1);
        setAnchorAndLead(index0, index1);
        dateSelectionModel.addSelectedInterval(calendarModel.getCalendarAt(index0).getTime(),
                calendarModel.getCalendarAt(index1).getTime());
    }

    public void removeIndexInterval(int index0, int index1) {
        removeSelectionInterval(index0, index1);
    }

    public void removeSelectionInterval(int index0, int index1) {
        System.out.println("Del " + index0 + " -> " + index1);
        setAnchorAndLead(index0, index1);
        dateSelectionModel.removeSelectedInterval(calendarModel.getCalendarAt(index0).getTime(),
                calendarModel.getCalendarAt(index1).getTime());
    }

    public void setSelectionInterval(int index0, int index1) {
        System.out.println("Set");
        clearSelection();
        addSelectionInterval(index0, index1);
    }

    public void insertIndexInterval(int index, int length, boolean before) {
        System.out.println("Ins");
        int insMinIndex = (before) ? index : index + 1;
        int insMaxIndex = (insMinIndex + length) - 1;
        addSelectionInterval(insMinIndex, insMaxIndex);
    }

    public void addListSelectionListener(ListSelectionListener l) {
 	    listenerList.add(ListSelectionListener.class, l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
 	    listenerList.remove(ListSelectionListener.class, l);
    }

    /**
     * @param firstIndex the first index in the interval
     * @param lastIndex the last index in the interval
     * @param isAdjusting true if this is the final change in a series of
     *		adjustments
     * @see EventListenerList
     */
    protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
        Object[] listeners = listenerList.getListenerList();
        ListSelectionEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListSelectionListener.class) {
                if (e == null) {
                    e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
                }
                ((ListSelectionListener)listeners[i+1]).valueChanged(e);
            }
        }
    }

    public CalendarTableModel getCalendarModel() {
        return calendarModel;
    }

    public DateSelectionModel getDateSelectionModel() {
        return dateSelectionModel;
    }

    public void setDateSelectionModel(DateSelectionModel dateSelectionModel) {
        this.dateSelectionModel = dateSelectionModel;
    }
}
