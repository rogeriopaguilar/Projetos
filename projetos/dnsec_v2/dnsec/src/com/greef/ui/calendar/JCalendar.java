package com.greef.ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.greef.ui.ArrowButton;

/**
 * A component to select a date.
 * @author Adrian BER
 */
public class JCalendar extends JPanel {

    /** The name for the month property. */
    public static final String MONTH_PROPERTY = "month";
    /** The name for the year property. */
    public static final String YEAR_PROPERTY = "year";

    /** The name for the year property. */
    public static final String DATE_PROPERTY = "date";

    /** The prefix for the color properties. */
    static final String COLOR_PREFIX = "Calendar.";
    /** The suffix for the background properties. */
    static final String BACKGROUND_SUFFIX = ".Background";
    /** The suffix for the foreground properties. */
    static final String FOREGROUND_SUFFIX = ".Foreground";
    /** The prefix for the font properties. */
    static final String FONT_PREFIX = "Calendar.";

    /** The possible color properties. */
    public static final String[] COLOR_PROPERTIES = new String[] {
        COLOR_PREFIX + "All" + BACKGROUND_SUFFIX,
        COLOR_PREFIX + "All" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "All.Selected" + BACKGROUND_SUFFIX,
        COLOR_PREFIX + "All.Selected" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "Today" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Today" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Today.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Today.Selected" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "PreviousMonth" + BACKGROUND_SUFFIX, COLOR_PREFIX + "PreviousMonth" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "PreviousMonth.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "PreviousMonth.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "NextMonth" + BACKGROUND_SUFFIX, COLOR_PREFIX + "NextMonth" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "NextMonth.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "NextMonth.Selected" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "Grid",
        COLOR_PREFIX + "Controls" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Controls" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "Header" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.1" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.1" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.2" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.2" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.3" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.3" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.4" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.4" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.5" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.5" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.6" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.6" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Header.WeekDay.7" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Header.WeekDay.7" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "WeekDay.1" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.1" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.1.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.1.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.2" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.2" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.2.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.2.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.3" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.3" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.3.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.3.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.4" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.4" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.4.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.4.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.5" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.5" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.5.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.5.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.6" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.6" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.6.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.6.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.7" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.7" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "WeekDay.7.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "WeekDay.7.Selected" + FOREGROUND_SUFFIX,

        COLOR_PREFIX + "Week.1" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.1" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.1.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.1.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.2" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.2" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.2.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.2.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.3" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.3" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.3.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.3.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.4" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.4" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.4.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.4.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.5" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.5" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.5.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.5.Selected" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.6" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.6" + FOREGROUND_SUFFIX,
        COLOR_PREFIX + "Week.6.Selected" + BACKGROUND_SUFFIX, COLOR_PREFIX + "Week.6.Selected" + FOREGROUND_SUFFIX,
        };

    /** The possible font properties. */
    public static final String[] FONT_PROPERTIES = new String[] {
        FONT_PREFIX + "All",
        FONT_PREFIX + "All.Selected",
        FONT_PREFIX + "Controls",

        FONT_PREFIX + "Today", FONT_PREFIX + "Today.Selected",

        FONT_PREFIX + "PreviousMonth", FONT_PREFIX + "PreviousMonth.Selected",
        FONT_PREFIX + "NextMonth", FONT_PREFIX + "NextMonth.Selected",

        FONT_PREFIX + "Header",
        FONT_PREFIX + "Header.WeekDay.1",
        FONT_PREFIX + "Header.WeekDay.2",
        FONT_PREFIX + "Header.WeekDay.3",
        FONT_PREFIX + "Header.WeekDay.4",
        FONT_PREFIX + "Header.WeekDay.5",
        FONT_PREFIX + "Header.WeekDay.6",
        FONT_PREFIX + "Header.WeekDay.7",

        FONT_PREFIX + "WeekDay.1", FONT_PREFIX + "WeekDay.1.Selected",
        FONT_PREFIX + "WeekDay.2", FONT_PREFIX + "WeekDay.2.Selected",
        FONT_PREFIX + "WeekDay.3", FONT_PREFIX + "WeekDay.3.Selected",
        FONT_PREFIX + "WeekDay.4", FONT_PREFIX + "WeekDay.4.Selected",
        FONT_PREFIX + "WeekDay.5", FONT_PREFIX + "WeekDay.5.Selected",
        FONT_PREFIX + "WeekDay.6", FONT_PREFIX + "WeekDay.6.Selected",
        FONT_PREFIX + "WeekDay.7", FONT_PREFIX + "WeekDay.7.Selected",

        FONT_PREFIX + "Week.1", FONT_PREFIX + "Week.1.Selected",
        FONT_PREFIX + "Week.2", FONT_PREFIX + "Week.2.Selected",
        FONT_PREFIX + "Week.3", FONT_PREFIX + "Week.3.Selected",
        FONT_PREFIX + "Week.4", FONT_PREFIX + "Week.4.Selected",
        FONT_PREFIX + "Week.5", FONT_PREFIX + "Week.5.Selected",
        FONT_PREFIX + "Week.6", FONT_PREFIX + "Week.6.Selected",
        };

    /** Constant value for the weekday column name meaning that the column name is
     * the first character in the weekday name
     */
    public static final int ONE = CalendarTableModel.ONE;

    /** Constant value for the weekday column name meaning that the column name is
     * the weekday short name.
     */
    public static final int SHORT = CalendarTableModel.SHORT;

    /** Constant value for the weekday column name meaning that the column name is
     * the weekday entire name.
     */
    public static final int LONG = CalendarTableModel.LONG;

    /** Constant value for controls position. */
    public static final int BOTTOM = SwingConstants.BOTTOM;

    /** Constant value for controls position. */
    public static final int TOP = SwingConstants.TOP;

    /** The mebedded table. */
    private JCalendarTable calendarTable;

    /** Where the controls are positioned. */
    private int controlsPosition = TOP;

    /** Show controls? */
    private boolean showControls = true;

    /** How much the month/year are increased by the second level controls. */
    private int controlsStep = 10;

    /** The enclosing controls panel. */
    private JPanel controlsPanel;

    /** The year control component. */
    private JLabel yearControl;
    private JPopupMenu yearMenu;

    private int minYearAmount = 10;
    private int maxYearAmount = 10;
    private boolean updateYearsOnChange = false;

    /** The month control component. */
    private JLabel monthControl;
    private JPopupMenu monthMenu;

    /** Creates a new calendar. */
    public JCalendar() {
        init();
        resetToDefaults();
    }

    /** Initializes the user interface. */
    private void init() {
        setLayout(new BorderLayout());

        // the embedded table
        calendarTable = new JCalendarTable();
        // catch property change event and forward them
        calendarTable.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChange(evt.getPropertyName(), evt.getOldValue(),
                        evt.getOldValue());
            }
        });
        // put the calendar table in a scroll pane in this component
        JScrollPane x = new JScrollPane(calendarTable);
        x.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        x.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(x, BorderLayout.CENTER);

        // the controls panel
        controlsPanel = new JPanel();
        add(controlsPanel,
                (controlsPosition == TOP ? BorderLayout.NORTH : BorderLayout.SOUTH));
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));

        // add controls
        controlsPanel.add(createControlButton(Calendar.YEAR, -1, 2));
        controlsPanel.add(createControlButton(Calendar.MONTH, -1));

        //the today button
//        JButton todayButton = new JButton("*") {
//            public String getToolTipText(MouseEvent e) {
//                return getTooltipDateFormat().format(new Date());
//            }
//        };
//        todayButton.setToolTipText("Today");
//        todayButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//        todayButton.setMargin(new Insets(0, 0, 0, 0));
//        todayButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                setTodayDate();
//            }
//        });
//        controlsPanel.add(todayButton);

        controlsPanel.add(Box.createHorizontalGlue());

        initMonthControl();
        controlsPanel.add(monthControl);

        controlsPanel.add(Box.createHorizontalStrut(3));

        initYearControl();
        controlsPanel.add(yearControl);

        controlsPanel.add(Box.createHorizontalGlue());

        controlsPanel.add(createControlButton(Calendar.MONTH, 1));

        controlsPanel.add(createControlButton(Calendar.YEAR, 1, 2));

        updateControlsFromTable();
    }

    /** Updates the month menu as a consequence of updating the months
     * (changing the calendar type) or the month names(changing the language)
     */
    private void updateMonthMenu() {
        String[] months = getDateFormatSymbols().getMonths();
        monthMenu.removeAll();
        for (int i = 0; i < months.length; i++) {
            JMenuItem x = new JMenuItem(months[i]);
            x.addActionListener(new DateActionListener(Calendar.MONTH, i));
            monthMenu.add(x);
        }
        if (monthControl != null) {
            updateControlsFromTable();
        }
    }

    /** Action listener that changes the month/year of this calendar.
     * It is associated with a field (MONTH/YEAR) and the new value.
     */
    private class DateActionListener implements ActionListener {
        private int field;
        private int value;

        public DateActionListener(int field, int value) {
            this.field = field;
            this.value = value;
        }

        public void actionPerformed(ActionEvent e) {
            if (field == Calendar.MONTH) {
                setMonth(value);
            } else {
                setYear(value);
            }
        }
    }

    /** Creates the controls for the month menu. */
    private void initMonthControl() {
        monthMenu = new JPopupMenu();
        updateMonthMenu();

        monthControl = new JLabel();
        monthControl.setBorder(null);
        monthControl.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int month = getMonth();
                int y = 0;
                for (int i = 0; i < month; i++) {
                    y -= monthMenu.getComponent(i).getPreferredSize().getHeight();
                }
                monthMenu.show(monthControl, 0, y);
            }
        });
    }

    /** Updates the year menu as a consequence of changing the number of years
     * displayed in the year selection menu.
     */
    private void updateYearMenu() {
        int year = getYear();
        yearMenu.removeAll();
        for (int i = year - minYearAmount; i <= year + maxYearAmount; i++) {
            JMenuItem x = new JMenuItem(String.valueOf(i));
            x.addActionListener(new DateActionListener(Calendar.YEAR, i));
            yearMenu.add(x);
        }
    }

    /** Initializes the year control.
     * @see #setMinYearAmount(int)
     * @see #setMaxYearAmount(int)
     * @see #setUpdateYearsOnChange(boolean)
     */
    private void initYearControl() {
        yearMenu = new JPopupMenu();
        updateYearMenu();

        yearControl = new JLabel();
        yearControl.setBorder(null);
        yearControl.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int year = getYear();
                int y = 0;
                for (int i = 0, n = yearMenu.getComponentCount(); i < n; i++) {
                    int val = Integer.parseInt(((JMenuItem)yearMenu.getComponent(i)).getText());
                    if (val < year) {
                        y -= yearMenu.getComponent(i).getPreferredSize().getHeight();
                    } else {
                        break;
                    }
                }
                yearMenu.show(yearControl, 0, y);
            }
        });
    }

    /** Returns the minimmum year amount.
     * @see #isUpdateYearsOnChange()
     */
    public int getMinYearAmount() {
        return minYearAmount;
    }

    /** Sets the minimmum year amount.
     * @see #isUpdateYearsOnChange()
     */
    public void setMinYearAmount(int minYearAmount) {
        this.minYearAmount = minYearAmount;
        updateYearMenu();
    }

    /** Sets the updateYearsOnChange property. The years in the year menu are
     * displayed from (currentYear - minYearAmount) to (currentYear + maxYearAmount).
     * If updateYearsOnChange property is set then every time the year is changed
     * (either the user selects a new year, either programatically) the years
     * in the year menu are changed accordingly. Otherwise the initial years are
     * always displayed in the menu.
     * @see #isUpdateYearsOnChange()
     */
    public boolean isUpdateYearsOnChange() {
        return updateYearsOnChange;
    }

    /** Sets the updateYearsOnChange property
     * @see #isUpdateYearsOnChange()
     */
    public void setUpdateYearsOnChange(boolean updateYearsOnChange) {
        this.updateYearsOnChange = updateYearsOnChange;
    }

    /** Returns the maximmum year amount.
     * @see #isUpdateYearsOnChange()
     */
    public int getMaxYearAmount() {
        return maxYearAmount;
    }

    /** Sets the maximmum year amount.
     * @see #isUpdateYearsOnChange()
     */
    public void setMaxYearAmount(int maxYearAmount) {
        this.maxYearAmount = maxYearAmount;
        updateYearMenu();
    }

    /** Creates a button for controlling month/year.
     * @param scope whether to create a button to control month or year
     * can be <code>Calendar.MONTH</code> or <code>Calendar.YEAR</code>.
     * @param stepType how the month/year is increased/decreased; if is
     * negative/positive the month/year is decreased/increased, if is
     * -1/-10 the month/year is decreased/increased by -1/controlsStep
     */
    private AbstractButton createControlButton(final int scope, final int stepType) {
        return createControlButton(scope, stepType, Math.abs(stepType) > 1 ? 2 : 1);
    }

    /** Creates a button for controlling month/year.
     * @param scope whether to create a button to control month or year
     * can be <code>Calendar.MONTH</code> or <code>Calendar.YEAR</code>.
     * @param stepType how the month/year is increased/decreased; if is
     * negative/positive the month/year is decreased/increased, if is
     * -1/-10 the month/year is decreased/increased by -1/controlsStep
     */
    private AbstractButton createControlButton(final int scope, final int stepType,
            final int arrows) {
        AbstractButton button = new ArrowButton(
                stepType < 0 ? SwingConstants.WEST : SwingConstants.EAST,
                arrows,
                5);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = (stepType < 0 ? -1 : 1)
                        * (Math.abs(stepType) > 1 ? controlsStep : 1);
                if (scope == Calendar.YEAR) {
                    addYear(amount);
                } else {
                    addMonth(amount);
                }
            }
        });
        button.setOpaque(false);
        return button;
    }

    /** Synchronizes the month/year controls with what's displayed in the enclosing table. */
    private void updateControlsFromTable() {
        monthControl.setText(getDateFormatSymbols()
            .getMonths()[calendarTable.getCalendarModel().getMonth()]);
        yearControl.setText(String.valueOf(calendarTable.getCalendarModel().getYear()));
    }

    /** Returns the month. */
    public int getMonth() {
        return calendarTable.getCalendarModel().getMonth();
    }

    /** Called when month was changed. */
    private void monthChanged(int oldMonthValue, int oldYearValue) {
        updateControlsFromTable();

        // fire property change
        firePropertyChange(MONTH_PROPERTY, oldMonthValue, getMonth());
        int newYearValue = getYear();
        if (oldYearValue != newYearValue) {
            firePropertyChange(YEAR_PROPERTY, oldYearValue, newYearValue);
        }

        // clear selection when changing the month in view
        calendarTable.getSelectionModel().clearSelection();

        calendarTable.repaint();
    }

    /** Sets the month. */
    public void setMonth(int month) {
        int oldValue = getMonth();
        int oldYearValue = getYear();
        calendarTable.getCalendarModel().setMonth(month);
        monthChanged(oldValue, oldYearValue);
    }

    /** Adds to the month the specified amount. */
    public void addMonth(int amount) {
        int oldValue = getMonth();
        int oldYearValue = getYear();
        calendarTable.getCalendarModel().addMonth(amount);
        monthChanged(oldValue, oldYearValue);
    }

    /** Returns the year. */
    public int getYear() {
        return calendarTable.getCalendarModel().getYear();
    }

    /** Returns the date. */
    public Date getDate() {
        return calendarTable.getCalendarModel().getDate();
    }

    
    /** Called when year was changed. */
    private void yearChanged(int oldValue) {
        updateControlsFromTable();

        // fire property change
        firePropertyChange(YEAR_PROPERTY, oldValue, getYear());

        // clear selection when changing the month in view
        calendarTable.getSelectionModel().clearSelection();

        if (updateYearsOnChange) {
            updateYearMenu();
        }

        calendarTable.repaint();
    }

    /** Sets the year. */
    public void setYear(int year) {
        int oldValue = getYear();
        calendarTable.getCalendarModel().setYear(year);
        yearChanged(oldValue);
    }

    /** Adds to the year the specified amount. */
    public void addYear(int amount) {
        int oldValue = getYear();
        calendarTable.getCalendarModel().addYear(amount);
        yearChanged(oldValue);
    }

    /** Sets this month/year calendar to the month/year of the current date. */
    public void setTodayDate() {
        int oldMonthValue = getMonth();
        int oldYearValue = getYear();
        calendarTable.getCalendarModel().setTodayDate();
        updateControlsFromTable();
        // fire property change
        int newMonthValue = getMonth();
        if (oldMonthValue != newMonthValue) {
            firePropertyChange(MONTH_PROPERTY, oldMonthValue, newMonthValue);
        }
        int newYearValue = getYear();
        if (oldYearValue != newYearValue) {
            firePropertyChange(YEAR_PROPERTY, oldYearValue, newYearValue);
        }
        // clear selection when changing the month in view
        if (oldMonthValue != newMonthValue && oldYearValue != newYearValue) {
            calendarTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Sets a specific date to this calendar 
     * @author RAguilar
     * */
    public void setDate(Date newDate) {
        int oldMonthValue = getMonth();
        int oldYearValue = getYear();
        Date oldDate = getDate();
        calendarTable.getCalendarModel().setDate(newDate);
        updateControlsFromTable();
        // fire property change
        int newMonthValue = getMonth();
        if (oldMonthValue != newMonthValue) {
            firePropertyChange(MONTH_PROPERTY, oldMonthValue, newMonthValue);
        }
        int newYearValue = getYear();
        if (oldYearValue != newYearValue) {
            firePropertyChange(YEAR_PROPERTY, oldYearValue, newYearValue);
        }
        if (oldDate.getTime() != newDate.getTime()) {
            firePropertyChange(DATE_PROPERTY, oldDate, newDate);
        }
        
        // clear selection when changing the month in view
        if (oldMonthValue != newMonthValue && oldYearValue != newYearValue) {
            calendarTable.getSelectionModel().clearSelection();
        }
        int rowCount = calendarTable.getRowCount();
        int columnCount= calendarTable.getColumnCount();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(newDate);
    	
        for(int contOne = 0; contOne < rowCount; contOne++)
        {
            for(int contTwo = 0; contTwo < columnCount; contTwo++)
            {
            	if( ("" + calendarTable.getModel().getValueAt(contOne, contTwo)).equals("" + cal.get(Calendar.DAY_OF_MONTH) ))
            	{
            		calendarTable.setRowSelectionInterval(contOne, contOne);
            		calendarTable.setColumnSelectionInterval(contTwo, contTwo);
            		contOne = rowCount;
            		contTwo = columnCount;
            	}
            }
        }
        
    }
    
    
    /** Returns the selected date or null if none is selected. */
    public Date getSelectedDate() {
        return calendarTable.getSelectedDate();
    }

    /** Returns the selected dates. */
    public Date[] getSelectedDates() {
        return calendarTable.getSelectedDates();
    }

    /** Returns the date format symbols used to display the month control contents,
     * the header, the tooltips.
     */
    public DateFormatSymbols getDateFormatSymbols() {
        return calendarTable.getDateFormatSymbols();
    }

    /** Sets the date format symbols used to display the month control contents,
     * the header, the tooltips.
     */
    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        calendarTable.setDateFormatSymbols(dateFormatSymbols);
        // update the month control
        updateMonthMenu();
    }

    /** Returns if the controls are shown. */
    public boolean isShowControls() {
        return showControls;
    }

    /** Sets whether or not the controls are shown. */
    public void setShowControls(boolean showControls) {
        if (this.showControls == showControls) {
            return;
        }

        if (showControls) {
            add(controlsPanel,
                    (controlsPosition == TOP ? BorderLayout.NORTH : BorderLayout.SOUTH));
        } else {
            remove(controlsPanel);
        }
        boolean oldValue = this.showControls;
        this.showControls = showControls;
        firePropertyChange("showControls", oldValue, showControls);
        doLayout();
        repaint();
    }

    /** Returns the controls position.
     * @return controls position, can be one of the value
     * <dl>
     *      <dt>{@link #BOTTOM}
     *      <dd>controls are under the calendar
     *      <dt>{@link #TOP}
     *      <dd>controls are above the calendar
     * </dl>
     */
    public int getControlsPosition() {
        return controlsPosition;
    }

    /** Returns the controls position.
     * @param controlsPosition controls position, can be one of the value
     * <dl>
     *      <dt>{@link #BOTTOM}
     *      <dd>controls are under the calendar
     *      <dt>{@link #TOP}
     *      <dd>controls are above the calendar
     * </dl>
     */
    public void setControlsPosition(int controlsPosition) {
        if (this.controlsPosition == controlsPosition) {
            return;
        }

        int oldValue = this.controlsPosition;
        if (controlsPosition == BOTTOM || controlsPosition == TOP) {
            this.controlsPosition = controlsPosition;
            add(controlsPanel,
                    (controlsPosition == TOP ? BorderLayout.NORTH : BorderLayout.SOUTH));
        } else {
            throw new IllegalArgumentException("Controls poisition can be only "
                    + TOP + " for top or " + BOTTOM + " for bottom.");
        }
        firePropertyChange("controlsPosition", oldValue, controlsPosition);
        doLayout();
        repaint();
    }

    public int getControlsStep() {
        return controlsStep;
    }

    private void setControlsStep(int controlsStep) {
        int oldValue = this.controlsStep;
        this.controlsStep = controlsStep;
        firePropertyChange("controlsStep", oldValue, controlsStep);
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
    public int getWeekDayDisplayType() {
        return calendarTable.getCalendarModel().getWeekDayColumnNameType();
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
     * @param weekDayDisplayType the display type
     * @throws IllegalArgumentException if the given display type is not one of the three
     * values
     */
    public void setWeekDayDisplayType(int weekDayDisplayType) {
        int oldValue = calendarTable.getCalendarModel().getWeekDayColumnNameType();
        calendarTable.getCalendarModel().setWeekDayColumnNameType(weekDayDisplayType);
        firePropertyChange("weekDayDisplayType", oldValue, weekDayDisplayType);
    }

    /** Returns whether or not the horizontal grid lines are shown. */
    public boolean getShowHorizontalLines() {
        return calendarTable.getShowHorizontalLines();
    }

    /** Sets whether or not the horizontal grid lines are shown. */
    public void setShowHorizontalLines(boolean show) {
        calendarTable.setShowHorizontalLines(show);
    }

    /** Returns whether or not the vertical grid lines are shown. */
    public boolean getShowVerticalLines() {
        return calendarTable.getShowVerticalLines();
    }

    /** Sets whether or not the vertical grid lines are shown. */
    public void setShowVerticalLines(boolean show) {
        calendarTable.setShowVerticalLines(show);
    }

    /**
     * Returns the selection mode.
     * @see #setSelectionMode(int)
     */
    public int getSelectionMode() {
        return calendarTable.getSelectionModel().getSelectionMode();
    }

    /** Sets the selection mode to allow only single selections,
     * a single contiguous interval, or multiple intervals..
     * @param mode the selection mode
     * @see #getSelectionMode()
     */
    public void setSelectionMode(int mode) {
        int oldValue = getSelectionMode();
        calendarTable.setSelectionMode(mode);
        firePropertyChange("selectionMode", oldValue, mode);
    }

    /** Returns whether or not by clicking on the header column the first day of week
     * is changed.
     */
    public boolean isChangingFirstDayOfWeekAllowed() {
        return calendarTable.isChangingFirstDayOfWeekAllowed();
    }

    /** Sets whether or not by clicking on the header column the first day of week
     * is changed.
     */
    public void setChangingFirstDayOfWeekAllowed(boolean changingFirstDayOfWeekAllowed) {
        calendarTable.setChangingFirstDayOfWeekAllowed(changingFirstDayOfWeekAllowed);
    }

    /** Returns if the calendar cell will have tooltips.
     * @see #setShowCellTooltips(boolean)
     * @see #getTooltipDateFormat()
     * @see #setTooltipDateFormat(DateFormat)
     * @see #getTooltipDatePattern()
     * @see #setTooltipDatePattern(String)
     */
    public boolean isShowCellTooltips() {
        return calendarTable.isShowCellTooltips();
    }

    /** Sets if the calendar cell will have tooltips.
     * @see #setShowCellTooltips(boolean)
     * @see #getTooltipDateFormat()
     * @see #setTooltipDateFormat(DateFormat)
     * @see #getTooltipDatePattern()
     * @see #setTooltipDatePattern(String)
     */
    public void setShowCellTooltips(boolean visible) {
        calendarTable.setShowCellTooltips(visible);
    }

    /** Returns the date format for the calendar cell dates tooltip. */
    public DateFormat getTooltipDateFormat() {
        return calendarTable.getTooltipDateFormat();
    }

    /** Sets the date format for the calendar cell dates tooltip. */
    public void setTooltipDateFormat(DateFormat dateFormat) {
        calendarTable.setTooltipDateFormat(dateFormat);
    }

    /** Returns the date format pattern for the calendar cell dates tooltip. */
    public String getTooltipDatePattern() {
        return calendarTable.getTooltipDatePattern();
    }

    /** Sets the date format pattern for the calendar cell dates tooltip. */
    public void setTooltipDatePattern(String pattern) {
        calendarTable.setTooltipDatePattern(pattern);
    }

    /** Returns a color property associated with this calendar.
     * Every property has a prefix and a suffix. The suffix can be either
     * ".Background" or ".Foreground" for the background, respectively foreground color.
     * The only property that makes an exception to this is "Calendar.Grid" which represents
     * the color for the grid between cells.
     * The possible prefix properties and the evaluation order are the same as
     * the font properties.
     * @see #getFont(java.lang.String)
     */
    public Color getColor(String property) {
        return calendarTable.getColor(property);
    }

    /** Sets a color property associated with this calendar.
     * @see #getColor(java.lang.String)
     */
    public void setColor(String property, Color color) {
        Color oldValue = calendarTable.getColor(property);
        if (color == oldValue) {
            return;
        }
        calendarTable.setColor(property, color);
        if (property.equals(COLOR_PREFIX + "Controls" + BACKGROUND_SUFFIX)) {
            for(int i = 0, n = controlsPanel.getComponentCount(); i < n; i++) {
                controlsPanel.getComponent(i).setBackground(color);
            }
        } else if (property.equals(COLOR_PREFIX + "Controls" + FOREGROUND_SUFFIX)) {
            for(int i = 0, n = controlsPanel.getComponentCount(); i < n; i++) {
                controlsPanel.getComponent(i).setForeground(color);
            }
        }
        firePropertyChange("Color." + property, oldValue, color);
    }

    /** Returns a font property associated with this calendar.
     * The possible properties and their associated meanings are:
     * <dl>
     *      <dt>Calendar.PreviousMonth
     *      <dd>the days in the calendar for the previous month to the one displayed
     *
     *      <dt>Calendar.PreviousMonth.Selected
     *      <dd>the days in the calendar for the previous month to the one displayed
     *          when selected
     *
     *      <dt>Calendar.NextMonth
     *      <dd>the days in the calendar for the next month to the one displayed
     *
     *      <dt>Calendar.NextMonth.Selected
     *      <dd>the days in the calendar for the next month to the one displayed
     *          when selected
     *
     *      <dt>Calendar.Today
     *      <dd>the current date in calendar
     *
     *      <dt>Calendar.Today.Selected
     *      <dd>the current date in calendar when selected
     *
     *      <dt>Calendar.Header
     *      <dd>the column names in the calendar header
     *
     *      <dt>Calendar.Header.WeekDay.x
     *      <dd>the column name in the calendar header associated with the x day of the week
     *
     *      <dt>Calendar.WeekDay.x
     *      <dd>the x day of the week in calendar (not the column number x)
     *
     *      <dt>Calendar.WeekDay.x.Selected
     *      <dd>the x day of the week in calendar when selected
     *
     *      <dt>Calendar.Week.x
     *      <dd>the x week in calendar
     *
     *      <dt>Calendar.Week.x.Selected
     *      <dd>the x week in calendar when selected
     *
     *      <dt>Calendar
     *      <dd>the days in the calendar
     *
     *      <dt>Calendar.Selected
     *      <dd>the days in the calendar when selected
     *
     *      <dt>Calendar.Controls
     *      <dd>the controls
     *
     * </dl>
     * The order in which the calendar looks for properties is the one displayed above.
     * It means that if we have <code>Calendar.WeekDay.1 = Times</code> and
     * <code>Calendar.Week.1 = Courier</code> then the first day in week of the first week
     * will be displayed with the <code>Times</code> font
     */
    public Font getFont(String property) {
        return calendarTable.getFont(property);
    }

    /** Sets a font property associated with this calendar.
     * @see #getFont(java.lang.String)
     */
    public void setFont(String property, Font font) {
        Font oldValue = calendarTable.getFont(property);
        if (font == oldValue) {
            return;
        }
        calendarTable.setFont(property, font);
        if (property.equals(FONT_PREFIX + "Controls")) {
            for(int i = 0, n = controlsPanel.getComponentCount(); i < n; i++) {
                controlsPanel.getComponent(i).setFont(font);
            }
        }
        firePropertyChange("Font." + property, oldValue, font);
    }

    /** Resets all the property for this calendar to default values. */
    public void resetToDefaults() {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setDateFormatSymbols(new DateFormatSymbols());
        setShowControls(true);
        setControlsPosition(JCalendar.TOP);
        setControlsStep(10);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setWeekDayDisplayType(JCalendar.ONE);
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
        setChangingFirstDayOfWeekAllowed(true);
        setTooltipDateFormat(new SimpleDateFormat("MMMM dd, yyyy"));
    }

    public DateSelectionModel getDateSelectionModel() {
        return ((CalendarSelectionModel)calendarTable.getSelectionModel())
            .getDateSelectionModel();
    }

    public void setDateSelectionModel(DateSelectionModel dateSelectionModel) {
        ((CalendarSelectionModel)calendarTable.getSelectionModel())
            .setDateSelectionModel(dateSelectionModel);
    }

    public void updateUI() {
        super.updateUI();
        if (yearMenu != null) {
            SwingUtilities.updateComponentTreeUI(yearMenu);
        }
        if (monthMenu != null) {
            SwingUtilities.updateComponentTreeUI(monthMenu);
        }
    }

}
