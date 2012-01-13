package com.greef.ui.calendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.greef.util.CalendarUtils;

/**
 * A table model that returns Integer's for every cell, every integer representing the
 * day in month displayed as a grid (the weekdays as columns and weeks as rows).
 * @author Adrian BER
 */
class CalendarTableModel implements TableModel {

    /** Constant value for the weekday column name meaning that the column name is
     * the first character in the weekday name.
     */
    public static final int ONE = 1;

    /** Constant value for the weekday column name meaning that the column name is
     * the weekday short name.
     */
    public static final int SHORT = 2;

    /** Constant value for the weekday column name meaning that the column name is
     * the weekday entire name.
     */
    public static final int LONG = 3;

    /** The weekday column name type. */
    private int weekDayColumnNameType = ONE;

    /** The date format symbols used for displaying the weekday column headers. */
    private DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();

    /** The month/year in this model. */
    private Calendar calendar = new GregorianCalendar();

    /** The model change listeners. */
    private Collection listeners = new LinkedList();

    /** Constructor. */
    public CalendarTableModel() {
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /** Returns the first day of week for this model.
     * @see #setFirstDayOfWeek(int)
     */
    public int getFirstDayOfWeek() {
        return calendar.getFirstDayOfWeek();
    }

    /** Sets the first day of week for this model.
     * @see #getFirstDayOfWeek()
     */
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        if (calendar.getFirstDayOfWeek() == firstDayOfWeek) {
            return;
        }
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        fireTableModelListeners(new TableModelEvent(this));
        fireTableModelListeners(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /** Returns the month for this model.
     * @return month for this calendar model
     */
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    /** Sets the month for this calendar.
     * @param month the month
     */
    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
    }

    /** Returns the month for this model.
     * @return date for this calendar model
     */
    public Date getDate() {
    	return calendar.getTime();
    }
    
    /** Adds the specified amount to the month of this calendar.
     * @param amount the amount to be added to the month of this calendar; if an overdue is
     * produced then the year is increased.
     */
    public void addMonth(int amount) {
        calendar.add(Calendar.MONTH, amount);
    }

    /** Returns the year for this model.
     * @return year for this calendar model
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    
    /** Sets the year for this calendar.
     * @param year the year
     */
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    /** Adds the specified amount to the year of this calendar.
     * @param amount the amount to be added to the year of this calendar.
     */
    public void addYear(int amount) {
        calendar.add(Calendar.YEAR, amount);
    }

    /**
     * Sets the month and year for this calendar.
     * @param month the month for this calendar
     * @param year the year for this calendar
     */
    public void setDate(int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
    }

    /**
     * Sets the month and year for this calendar to the month and year of the current date.
     */
    public void setTodayDate() {
        calendar.setTime(new Date());
    }

    public void setDate(Date data) {
        calendar.setTime(data);
    }
    
    
    public int getColumnCount() {
        return calendar.getMaximum(Calendar.DAY_OF_WEEK);
    }

    public int getRowCount() {
        return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /** Returns possible maximum number of rows aka the number of weeks in the
     * calendar month.
     */
    public int getMaxRowCount() {
        return calendar.getMaximum(Calendar.WEEK_OF_MONTH);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Class getColumnClass(int columnIndex) {
        return Integer.class;
    }

    /** Returns the underlying calendar with the first day in month as his date. */
    Calendar getCalendar() {
        return calendar;
    }

    /** Returns the calendar with the date at the specified position;
     * position = (row - 1) * maxColumns + column.
     */
    public Calendar getCalendarAt(int pos) {
        int cols = getColumnCount();
        return getCalendarAt(pos / cols, pos % cols);
    }

    /** Returns the calendar with the date at the specified row and column. */
    public Calendar getCalendarAt(int rowIndex, int columnIndex) {
        Calendar aux = (Calendar)calendar.clone();
        aux.set(Calendar.WEEK_OF_MONTH, rowIndex + 1);
        aux.set(Calendar.DAY_OF_WEEK, getDayOfWeek(columnIndex));
        return aux;
    }

    /** Returns the position for the specified calendar in this model. */
    public int getPositionFor(Calendar calendar) {
        Calendar firstPos = getCalendarAt(0);
        int aux = CalendarUtils.diffInDays(calendar, firstPos);
        if (aux < 0 || aux >= getRowCount() * getColumnCount()) {
            aux = -1;
        }
        return aux;
    }

    /** Returns the position for the specified date in this model. */
    public int getPositionFor(Date date) {
        Calendar aux = (Calendar)calendar.clone();
        aux.setTime(date);
        return getPositionFor(aux);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return new Integer(getCalendarAt(rowIndex, columnIndex).get(Calendar.DAY_OF_MONTH));
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new IllegalStateException("You cannot set value for a CalendarTableModel.");
    }

    /** Returns how the column names will be displayed. The possible values are
     * <dl>
     *      <dt>{@link #ONE}
     *      <dd>only the first character of the week day will be displayed
     *      <dt>{@link #SHORT}
     *      <dd>the short name of the week day will be displayed
     *      <dt>{@link #LONG}
     *      <dd>the entire name of the week day will be displayed
     * </dl>
     */
    public int getWeekDayColumnNameType() {
        return weekDayColumnNameType;
    }

    /** Sets how the column names will be displayed. The possible values are
     * <dl>
     *      <dt>{@link #ONE}
     *      <dd>only the first character of the week day will be displayed
     *      <dt>{@link #SHORT}
     *      <dd>the short name of the week day will be displayed
     *      <dt>{@link #LONG}
     *      <dd>the entire name of the week day will be displayed
     * </dl>
     * @param weekDayColumnNameType the display type
     * @throws IllegalArgumentException if the given display type is not one of the three
     * values
     */
    public void setWeekDayColumnNameType(int weekDayColumnNameType) {
        if (weekDayColumnNameType != ONE && weekDayColumnNameType != SHORT
                && weekDayColumnNameType != LONG) {
            throw new IllegalArgumentException("Week days display type can be only "
                    + ONE + " for first character, " + SHORT + " for short names and "
                    + LONG + " for long names.");
        }
        this.weekDayColumnNameType = weekDayColumnNameType;
        fireTableModelListeners(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /** Returns the week day corresponding to the given column.
     * @param columnIndex the column.
     * @return the corresponding column name.
     */
    public int getDayOfWeek(int columnIndex) {
        int max = calendar.getMaximum(Calendar.DAY_OF_WEEK);
        int i = (columnIndex + calendar.getFirstDayOfWeek()) % max;
        i = (i == 0 ? max : i);
        return i;
    }

    public String getColumnName(int columnIndex) {
        int i = getDayOfWeek(columnIndex);
        if (weekDayColumnNameType == ONE) {
            return dateFormatSymbols.getWeekdays()[i].substring(0, 1);
        } else if (weekDayColumnNameType == SHORT) {
            return dateFormatSymbols.getShortWeekdays()[i];
        } else {
            return dateFormatSymbols.getWeekdays()[i];
        }
    }

    /** Returns the date format symbols used to determine the column name. */
    public DateFormatSymbols getDateFormatSymbols() {
        return dateFormatSymbols;
    }

    /** Sets the date format symbols used to determine the column name. */
    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        this.dateFormatSymbols = dateFormatSymbols;
        fireTableModelListeners(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /** Adds the specified model listener to receive change events from this model.
     * @param l the change listener.
     * If l is null, no exception is thrown and no action is performed.
     */
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    /** Removes the specified model listener so it no longer receive events from this model.
     * @param l the change listener.
     * If l is null, no exception is thrown and no action is performed.
     */
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    /** Reports a change event in the table model. */
    protected void fireTableModelListeners(TableModelEvent e) {
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
            TableModelListener tableModelListener = (TableModelListener) iterator.next();
            tableModelListener.tableChanged(e);
        }
    }
}
