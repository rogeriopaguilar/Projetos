package com.greef.ui.calendar;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Adrian BER
 */
class CalendarSelectionModel2 implements ListSelectionModel {

    private int selectionMode = ListSelectionModel.SINGLE_SELECTION;

    private JCalendarTable calendarTable;

    private Collection selectedDates = new TreeSet();
    private Collection listeners = new HashSet();

    private Map listSelectionModels = new HashMap();
    private ListSelectionModel currentListSelectionModel = null;

    private CalendarTableModel calendarModel;

    private boolean valueIsAdjusting;

    public CalendarSelectionModel2(JCalendarTable calendarTable) {
        this.calendarTable = calendarTable;
        calendarModel = calendarTable.getCalendarModel();
    }

    public void fireChangeEvent() {
        ChangeEvent ev = new ChangeEvent(this);
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
            ChangeListener l = (ChangeListener)iterator.next();
            l.stateChanged(ev);
        }
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    public void addSelectedInterval(Date from, Date to) {
        if (from.compareTo(to) > 0) {
            return;
        }
        Calendar c = (Calendar)calendarModel.getCalendar().clone();
        c.setTime(from);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        while (c.getTime().compareTo(to) <= 0) {
            addSelectedDate(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void addSelectedDate(Date date) {
        selectedDates.add(date);
    }

    public void removeSelectedInterval(Date from, Date to) {

    }

    public void removeSelectedDate(Date date) {
        selectedDates.remove(date);
    }

    public void setSelectedInterval(Date from, Date to) {

    }

    public int getAnchorSelectionIndex() {
        return currentListSelectionModel.getAnchorSelectionIndex();
    }

    public int getLeadSelectionIndex() {
        return currentListSelectionModel.getLeadSelectionIndex();
    }

    public int getMaxSelectionIndex() {
        return currentListSelectionModel.getMaxSelectionIndex();
    }

    public int getMinSelectionIndex() {
        return currentListSelectionModel.getMinSelectionIndex();
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public boolean getValueIsAdjusting() {
        return valueIsAdjusting;
    }

    public void setValueIsAdjusting(boolean valueIsAdjusting) {
        this.valueIsAdjusting = valueIsAdjusting;
    }

    public void clearSelection() {
    }

    public boolean isSelectionEmpty() {
        return currentListSelectionModel.isSelectionEmpty();
    }

    public void setAnchorSelectionIndex(int index) {
        currentListSelectionModel.setAnchorSelectionIndex(index);
    }

    public void setLeadSelectionIndex(int index) {
        currentListSelectionModel.setLeadSelectionIndex(index);
    }

    public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
        for (Iterator it = listSelectionModels.keySet().iterator(); it.hasNext();) {
            ((ListSelectionModel)listSelectionModels.get(it.next()))
                    .setSelectionMode(selectionMode);
        }
    }

    public boolean isSelectedIndex(int index) {
        return currentListSelectionModel.isSelectedIndex(index);
    }

    public void addSelectionInterval(int index0, int index1) {
        currentListSelectionModel.addSelectionInterval(index0, index1);
    }

    public void removeIndexInterval(int index0, int index1) {
        currentListSelectionModel.removeIndexInterval(index0, index1);
    }

    public void removeSelectionInterval(int index0, int index1) {
        currentListSelectionModel.removeIndexInterval(index0, index1);
    }

    public void setSelectionInterval(int index0, int index1) {
        currentListSelectionModel.setSelectionInterval(index0, index1);
    }

    public void insertIndexInterval(int index, int length, boolean before) {
    }

    public void addListSelectionListener(ListSelectionListener x) {
        for (Iterator it = listSelectionModels.keySet().iterator(); it.hasNext();) {
            ((ListSelectionModel)listSelectionModels.get(it.next()))
                    .addListSelectionListener(x);
        }
    }

    public void removeListSelectionListener(ListSelectionListener x) {
        for (Iterator it = listSelectionModels.keySet().iterator(); it.hasNext();) {
            ((ListSelectionModel)listSelectionModels.get(it.next()))
                    .removeListSelectionListener(x);
        }
    }

    class Updater implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                addSelectedInterval(calendarModel.getCalendarAt(e.getFirstIndex()).getTime(),
                        calendarModel.getCalendarAt(e.getLastIndex()).getTime());
            }
        }

    }

}
