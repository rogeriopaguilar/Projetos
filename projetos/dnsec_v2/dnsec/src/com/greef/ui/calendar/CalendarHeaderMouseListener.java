package com.greef.ui.calendar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

/**
 * Mouse listener to be used by the calendar table to change the first day of week when
 * clicking on a column in the header. This will be installed like
 * <code>calendarTable.getTableHeader().addMouseListener(
 * new CalendarHeaderMouseListener())</code>
 * @see JCalendarTable
 * @author Adrian BER
 */
class CalendarHeaderMouseListener extends MouseAdapter {

    public void mouseClicked(MouseEvent ev) {
        JTable table;
        CalendarTableModel calendarModel;

        // get the source table
        table = ((JTableHeader) ev.getSource()).getTable();

        // if the table is a JCalendarTable
        if (table instanceof JCalendarTable) {
            calendarModel = ((JCalendarTable) table).getCalendarModel();
        }
        // if the table has a calendar model
        else if (table.getModel() instanceof CalendarTableModel) {
            calendarModel = (CalendarTableModel) table.getModel();
        } else {
            throw new IllegalArgumentException("CalendarHeaderMouseListener: " +
                "Table should be an JCalendarTable or should have a calendarTableModel");
        }

        // if left mouse click
        if (SwingUtilities.isLeftMouseButton(ev)) {

            // get the column on which we clicked
            int col = table.getTableHeader().columnAtPoint(ev.getPoint());

            // change first day of week if necessary
            int dayOfWeek = calendarModel.getDayOfWeek(col);
            if (calendarModel.getFirstDayOfWeek() != dayOfWeek) {
                calendarModel.setFirstDayOfWeek(dayOfWeek);
            }
        }
    }
}
