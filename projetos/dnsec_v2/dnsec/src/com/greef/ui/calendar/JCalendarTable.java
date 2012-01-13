package com.greef.ui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 * @author Adrian BER
 */
class JCalendarTable extends JTable {

    private CalendarTableModel calendarModel = new CalendarTableModel();

    private boolean showCellTooltips;
    private DateFormat tooltipDateFormat;

    private Map colors = new HashMap();

    private Map fonts = new HashMap();

    private boolean changingFirstDayOfWeekAllowed = true;

    private MouseListener changeFirstDayOfWeekEvent = new CalendarHeaderMouseListener();

    public JCalendarTable() {
        setTooltipDateFormat(new SimpleDateFormat("MMMM dd, yyyy"));

        setModel(calendarModel);

        // this is for selecting days from different months
//        setSelectionModel(new CalendarSelectionModel(calendarModel));

        CalendarRenderer renderer = new CalendarRenderer();
        setDefaultRenderer(Integer.class, renderer);

        setTableHeader(new JTableHeader(getColumnModel()) {
            public void columnAdded(TableColumnModelEvent e) {
                super.columnAdded(e);
                for (int i = e.getFromIndex(), n = e.getToIndex(); i <= n; i++) {
                    getColumnModel().getColumn(i).setMinWidth(20);
                    getColumnModel().getColumn(i).setPreferredWidth(25);
                }
            }

            public String getToolTipText(MouseEvent event) {
                int col = columnAtPoint(event.getPoint());
                return calendarModel.getDateFormatSymbols().getWeekdays()
                        [calendarModel.getDayOfWeek(col)];
            }
        });
        getTableHeader().setDefaultRenderer(renderer);

        // add a mouse listener for the header
        getTableHeader().addMouseListener(changeFirstDayOfWeekEvent);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setShowGrid(false);
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
        setCellSelectionEnabled(true);

        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setPreferredScrollableViewportSize( new Dimension (
                  25 * calendarModel.getCalendar().getMaximum(Calendar.DAY_OF_WEEK),
                  18 * calendarModel.getCalendar().getMaximum(Calendar.WEEK_OF_MONTH)
                  ));
        JTableHeader header = getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
    }

    public void setModel(TableModel dataModel) {
        if (!(dataModel instanceof CalendarTableModel) && getModel() != null) {
            throw new IllegalArgumentException(
                    "JCalendarTable accepts only CalendarTableModel.");
        }
        super.setModel(dataModel);
    }

    public CalendarTableModel getCalendarModel() {
        return calendarModel;
    }

    public Date getSelectedDate() {
        int rows = getRowCount();
        int cols = getColumnCount();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isCellSelected(i, j)) {
                    return calendarModel.getCalendarAt(i, j).getTime();
                }
            }
        }
        return null;
    }

    public Date[] getSelectedDates() {
        int rows = getRowCount();
        int cols = getColumnCount();
        Date[] x = new Date[rows * cols];
        int n = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isCellSelected(i, j)) {
                    x[n++] = (calendarModel.getCalendarAt(i, j).getTime());
                }
            }
        }
        Date[] sel = new Date[n];
        System.arraycopy(x, 0, sel, 0, n);
        return sel;
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return calendarModel.getDateFormatSymbols();
    }

    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        DateFormatSymbols oldValue = getDateFormatSymbols();
        if (oldValue.equals(dateFormatSymbols)) {
            return;
        }
        calendarModel.setDateFormatSymbols(dateFormatSymbols);
        if (tooltipDateFormat instanceof SimpleDateFormat) {
            ((SimpleDateFormat)tooltipDateFormat).setDateFormatSymbols(dateFormatSymbols);
        }
        firePropertyChange("dateFormatSymbols", oldValue, dateFormatSymbols);
    }

    /** Returns if the calendar cell will have tooltips.
     * @see #setShowCellTooltips(boolean)
     * @see #getTooltipDateFormat()
     * @see #setTooltipDateFormat(DateFormat)
     */
    public boolean isShowCellTooltips() {
        return showCellTooltips;
    }

    /** Sets if the calendar cell will have tooltips.
     * @see #isShowCellTooltips()
     * @see #getTooltipDateFormat()
     * @see #setTooltipDateFormat(DateFormat)
     */
    public void setShowCellTooltips(boolean visible) {
        if (visible == showCellTooltips) {
            return;
        }
        boolean oldValue = showCellTooltips;
        showCellTooltips = visible;
        firePropertyChange("showCellTooltips", oldValue, visible);
    }

    public DateFormat getTooltipDateFormat() {
        return tooltipDateFormat;
    }

    public void setTooltipDateFormat(DateFormat tooltipDateFormat) {
        if (this.tooltipDateFormat == tooltipDateFormat ||
                this.tooltipDateFormat != null && this.tooltipDateFormat.equals(tooltipDateFormat)) {
            return;
        }
        DateFormat oldValue = getTooltipDateFormat();
        this.tooltipDateFormat = tooltipDateFormat;
        if (tooltipDateFormat != null) {
            tooltipDateFormat.setCalendar((Calendar)calendarModel.getCalendar().clone());
        }
        firePropertyChange("tooltipDateFormat", oldValue, tooltipDateFormat);
    }

    /** Returns the date format pattern for the calendar cell dates tooltip. */
    public String getTooltipDatePattern() {
        DateFormat f = getTooltipDateFormat();
        if (f instanceof SimpleDateFormat) {
            return ((SimpleDateFormat)f).toPattern();
        }
        return null;
    }

    /** Sets the date format pattern for the calendar cell dates tooltip. */
    public void setTooltipDatePattern(String pattern) {
        setTooltipDateFormat(new SimpleDateFormat(pattern, getDateFormatSymbols()));
    }

    public boolean isChangingFirstDayOfWeekAllowed() {
        return changingFirstDayOfWeekAllowed;
    }

    public void setChangingFirstDayOfWeekAllowed(boolean changingFirstDayOfWeekAllowed) {
        if (this.changingFirstDayOfWeekAllowed == changingFirstDayOfWeekAllowed) {
            return;
        }
        boolean oldValue = this.changingFirstDayOfWeekAllowed;
        this.changingFirstDayOfWeekAllowed = changingFirstDayOfWeekAllowed;
        if (changingFirstDayOfWeekAllowed) {
            getTableHeader().addMouseListener(changeFirstDayOfWeekEvent);    
        } else {
            getTableHeader().removeMouseListener(changeFirstDayOfWeekEvent);
        }
        firePropertyChange("changingFirstDayOfWeekAllowed", oldValue, changingFirstDayOfWeekAllowed);
    }

    public Color getColor(String property) {
        return (Color)colors.get(property);
    }

    public void setColor(String property, Color color) {
        if (color == null) {
            colors.remove(property);
        } else {
            colors.put(property, color);
            if (property.equals(JCalendar.COLOR_PREFIX + "Grid")) {
                setGridColor(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "All" + JCalendar.BACKGROUND_SUFFIX)) {
                setBackground(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "All" + JCalendar.FOREGROUND_SUFFIX)) {
                setForeground(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "All.Selected" + JCalendar.BACKGROUND_SUFFIX)) {
                setSelectionBackground(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "All.Selected" + JCalendar.FOREGROUND_SUFFIX)) {
                setSelectionForeground(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "Header" + JCalendar.BACKGROUND_SUFFIX)) {
                getTableHeader().setBackground(color);
            } else if (property.equals(JCalendar.COLOR_PREFIX + "Header" + JCalendar.FOREGROUND_SUFFIX)) {
                getTableHeader().setForeground(color);
            }
            if (property.indexOf("Header") >= 0) {
                getTableHeader().repaint();
            } else {
                repaint();
            }
        }
    }

    public Font getFont(String property) {
        return (Font)fonts.get(property);
    }

    public void setFont(String property, Font font) {
        if (font == null) {
            fonts.remove(property);
        } else {
            fonts.put(property, font);
            if (property.equals(JCalendar.FONT_PREFIX + "All")) {
                setFont(font);
            } if (property.equals(JCalendar.FONT_PREFIX + ".Header")) {
                getTableHeader().setFont(font);
            }
            if (property.indexOf("Header") >= 0) {
                getTableHeader().repaint();
            } else {
                repaint();
            }
        }
    }

    public String getToolTipText(MouseEvent event) {
        if (!showCellTooltips || tooltipDateFormat == null) {
            return null;
        }
        int row = rowAtPoint(event.getPoint());
        int col = columnAtPoint(event.getPoint());
        return tooltipDateFormat.format(calendarModel.getCalendarAt(row, col).getTime());
    }
}
