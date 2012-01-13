package com.greef.ui.calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This is a renderer that can be used for both header and cells.
 * This header renderer is a decorator for another header renderer.
 * If you want to install this as a header renderer you should do something like
 * <code>table.getTableHeader().setDefaultRenderr(calendarRenderer)</code>.
 * If you want to install this as a cell renderer you should do something like
 * <code>table.setDefaultRenderr(Integer.class, calendarRenderer)</code>.
 * @author Adrian Ber
 */
class CalendarRenderer implements TableCellRenderer {

    /** The wrapped cell renderer. */
    private TableCellRenderer renderer;

    /**
     * Creates a table cell renderer with an order icon that wrapps a
     * DefaultTableCellRenderer.
     */
    public CalendarRenderer() {
        this(new DefaultTableCellRenderer());
    }

    /**
     * Creates a table cell renderer with an order icon.
     *
     * @param renderer the renderer to be wrapped
     */
    public CalendarRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns the table cell renderer.
     *
     * @param table      the <code>JTable</code>
     * @param value      the value to assign to the cell at <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param isFocus    true if cell has focus
     * @param row        the row of the cell to render
     * @param column     the column of the cell to render
     * @return the default table cell renderer
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // the component returned by the wrapped renderer
        Component c = renderer.getTableCellRendererComponent(table, value, isSelected,
            hasFocus, row, column);
//        if (c instanceof JComponent) {
//            ((JComponent) c).setOpaque(false);
//        }

        if (c instanceof JLabel) {
            ((JLabel)c).setHorizontalAlignment(row < 0 ? JLabel.CENTER : JLabel.RIGHT);
        }

        // get the date at the current row and column
        JCalendarTable calendarTable = (JCalendarTable) table;
        CalendarTableModel calendarModel = calendarTable.getCalendarModel();
        Calendar calendar = calendarModel.getCalendarAt(row, column);
        Calendar today = (Calendar)calendarModel.getCalendar().clone();
        today.setTime(new Date());

        int weekday = calendarModel.getDayOfWeek(column);
        int week = row + 1;

        Color bgColor = null;
        Color fgColor = null;
        Font font = null;

        // get the properties for previous month
        if (row >= 0 && calendar.get(Calendar.MONTH) < calendarModel.getMonth()) {
            String prefix = "Calendar.PreviousMonth" + (isSelected ? ".Selected" : "");
            if (bgColor == null) {
                bgColor = calendarTable.getColor(prefix + ".Background");
            }
            if (fgColor == null) {
                fgColor = calendarTable.getColor(prefix + ".Foreground");
            }
            if (font == null) {
                font = calendarTable.getFont(prefix);
            }

        }

        // if not in header get the properties for next month
        if (row >= 0 && calendar.get(Calendar.MONTH) > calendarModel.getMonth()) {
            String prefix = "Calendar.NextMonth" + (isSelected ? ".Selected" : "");
            if (bgColor == null) {
                bgColor = calendarTable.getColor(prefix + ".Background");
            }
            if (fgColor == null) {
                fgColor = calendarTable.getColor(prefix + ".Foreground");
            }
            if (font == null) {
                font = calendarTable.getFont(prefix);
            }
        }

        // if not in header get the properties for today
        if (row >= 0 && sameDay(calendar, today)) {
            String prefix = "Calendar.Today" + (isSelected ? ".Selected" : "");
            if (bgColor == null) {
                bgColor = calendarTable.getColor(prefix + ".Background");
            }
            if (fgColor == null) {
                fgColor = calendarTable.getColor(prefix + ".Foreground");
            }
            if (font == null) {
                font = calendarTable.getFont(prefix);
            }
        }

        // get the properties for weekdays
        String prefix = "Calendar" + (row < 0 ? ".Header" : "") + ".WeekDay." + weekday
                + (row >= 0 && isSelected ? ".Selected" : "");
        if (bgColor == null) {
            bgColor = calendarTable.getColor(prefix + ".Background");
        }
        if (fgColor == null) {
            fgColor = calendarTable.getColor(prefix + ".Foreground");
        }
        if (font == null) {
            font = calendarTable.getFont(prefix);
        }

        // if not in header get the week properties
        if (row >= 0) {
            prefix = "Calendar.Week." + week + (isSelected ? ".Selected" : "");
            if (bgColor == null) {
                bgColor = calendarTable.getColor(prefix + ".Background");
            }
            if (fgColor == null) {
                fgColor = calendarTable.getColor(prefix + ".Foreground");
            }
            if (font == null) {
                font = calendarTable.getFont(prefix);
            }
        }

        // get the properties from table or header
        if (bgColor == null) {
            bgColor = (row >= 0 ?
                    isSelected ? calendarTable.getSelectionBackground()
                        : calendarTable.getBackground()
                    : calendarTable.getTableHeader().getBackground());
        }
        if (fgColor == null) {
            fgColor = (row >= 0 ?
                    isSelected ? calendarTable.getSelectionForeground()
                        : calendarTable.getForeground()
                    : calendarTable.getTableHeader().getForeground());
        }
        if (font == null && isSelected && row >= 0) {
            font = calendarTable.getFont("Calendar.Selected");
        }
        if (font == null) {
            font = (row >= 0 ? calendarTable.getFont()
                    : calendarTable.getTableHeader().getFont());
        }

        // set the properties
        c.setBackground(bgColor);
        c.setForeground(fgColor);
        c.setFont(font);

        return c;
    }

    /** Returns true if the given calendars are in the same day. */
    private boolean sameDay(Calendar a, Calendar b) {
        return (a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH))
                && (a.get(Calendar.MONTH) == b.get(Calendar.MONTH))
                && (a.get(Calendar.YEAR) == b.get(Calendar.YEAR));
    }

}
