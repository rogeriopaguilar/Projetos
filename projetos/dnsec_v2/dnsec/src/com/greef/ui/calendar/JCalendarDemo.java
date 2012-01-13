package com.greef.ui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.greef.ui.ArrowButton;
import com.greef.ui.HTMLColorsChooserPanel;
import com.greef.ui.LnFColorsChooserPanel;
import com.greef.ui.LnFComboBox;
import com.greef.ui.NumberDocument;
import com.greef.ui.font.JFontChooser;

/**
 * @author Adrian BER
 */
public class JCalendarDemo extends JPanel {

    private static final int[] SELECTION_TYPES = new int[] {
            ListSelectionModel.SINGLE_SELECTION,
            ListSelectionModel.SINGLE_INTERVAL_SELECTION,
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION};
    private static final String[] SELECTION_NAMES = new String[] {
            "single", "single interval", "multiple interval"};

    private static final int[] WEEKDAY_DISPLAY_TYPES = new int[] {
            JCalendar.ONE, JCalendar.SHORT, JCalendar.LONG};
    private static final String[] WEEKDAY_DISPLAY_NAMES = new String[] {
            "one", "short", "long"};

    private static final String[] TOOLTIP_PATTERNS = new String[] {
        "MM/dd/yyyy", "dd/MM/yyyy", "MMMM dd, yyyy", "dd MMMM yyyy"
    };

    private JCalendar calendar;

    private JCheckBox controlsShowCheckbox;
    private JRadioButton controlsTopRadioButton;
    private JRadioButton controlsBottomRadioButton;

    private JCheckBox updateYearsOnChangeCheckBox;
    private Spinner minYearAmountSpinner;
    private Spinner maxYearAmountSpinner;

    private JComboBox weekdayDisplayComboBox;

    private JComboBox selectionModeComboBox;

    private JCheckBox showHorizontalGridCheckbox;

    private JCheckBox showVerticalGridCheckbox;

    private JCheckBox firstDayOfWeekCheckbox;

    private JCheckBox showTooltipCheckBox;

    private JComboBox tooltipPatternsComboBox;

    private JComboBox colorsComboBox;

    private JButton colorButton;

    private JColorChooser colorChooser;

    private JDialog colorChooserDialog;

    private JComboBox fontsComboBox;

    private JFontChooser fontChooser;

    private JDialog fontChooserDialog;

    private JButton fontButton;

    private JTextArea sampleCodeTextArea;

    private DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    private JTextArea outputTextArea;

    public JCalendarDemo() {
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());

        calendar = new JCalendar();
        calendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        add(calendar, new GridBagConstraints(0, 0, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JPanel controlsPanel = new JPanel();
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
        controlsPanel.setLayout(new GridBagLayout());
        add(controlsPanel, new GridBagConstraints(1, 0, 1, 1, 0, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0);

        controlsPanel.add(new JLabel("Look'n'feel:"), c);
        LnFComboBox lnfComboBox = new LnFComboBox(this);
        c.gridx++;
        controlsPanel.add(lnfComboBox, c);

        controlsShowCheckbox = new JCheckBox("Show controls");
        controlsShowCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = controlsShowCheckbox.isSelected();
                calendar.setShowControls(sel);
                controlsTopRadioButton.setEnabled(sel);
                controlsBottomRadioButton.setEnabled(sel);
                outputSampleCode("calendar.setShowControls(" + sel + ");");
            }
        });
        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(controlsShowCheckbox, c);

        controlsPanel.add(new JLabel("Controls position:"), c);
        controlsTopRadioButton = new JRadioButton("top");
        controlsTopRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controlsTopRadioButton.isSelected()) {
                    calendar.setControlsPosition(JCalendar.TOP);
                    outputSampleCode("calendar.setControlsPosition(JCalendar.TOP);");
                }
            }
        });
        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(controlsTopRadioButton, c);

        controlsBottomRadioButton = new JRadioButton("bottom");
        controlsBottomRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controlsBottomRadioButton.isSelected()) {
                    calendar.setControlsPosition(JCalendar.BOTTOM);
                    outputSampleCode("calendar.setControlsPosition(JCalendar.BOTTOM);");
                }
            }
        });
        c.gridx++;
        controlsPanel.add(controlsBottomRadioButton, c);

        ButtonGroup controlsPositionGroup = new ButtonGroup();
        controlsPositionGroup.add(controlsTopRadioButton);
        controlsPositionGroup.add(controlsBottomRadioButton);


        updateYearsOnChangeCheckBox = new JCheckBox("Update years menu on change");
        updateYearsOnChangeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((JCheckBox)e.getSource()).isSelected();
                calendar.setUpdateYearsOnChange(sel);
                outputSampleCode("calendar.setUpdateYearsOnChange(" + sel + ");");
            }
        });
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        controlsPanel.add(updateYearsOnChangeCheckBox, c);
        c.gridwidth = 1;


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Min year amount:"), c);

        c.gridx++;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        minYearAmountSpinner = new Spinner(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                int x = minYearAmountSpinner.getValue();
                calendar.setMinYearAmount(x);
                outputSampleCode("calendar.setMinYearAmount(" + x + ");");
            }
        });
        controlsPanel.add(minYearAmountSpinner, c);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Max year amount:"), c);

        c.gridx++;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        maxYearAmountSpinner = new Spinner(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                int x = maxYearAmountSpinner.getValue();
                calendar.setMaxYearAmount(x);
                outputSampleCode("calendar.setMaxYearAmount(" + x + ");");
            }
        });
        controlsPanel.add(maxYearAmountSpinner, c);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Date symbols:"), c);
        JComboBox localesComboBox = new JComboBox(Locale.getAvailableLocales());
        localesComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Locale l = (Locale)((JComboBox)e.getSource()).getSelectedItem();
                calendar.setDateFormatSymbols(new DateFormatSymbols(l));
                outputSampleCode("calendar.setDateFormatSymbols(new DateFormatSymbols("
                        + "new Locale(\"" + l.getLanguage() + "\", \""
                        + l.getCountry() + "\", \"" + l.getVariant() + "\"));");
            }
        });
        c.gridx++;
        controlsPanel.add(localesComboBox, c);

        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Display weekdays:"), c);
        weekdayDisplayComboBox = new JComboBox(WEEKDAY_DISPLAY_NAMES);
        weekdayDisplayComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selIndex = ((JComboBox)e.getSource()).getSelectedIndex();
                calendar.setWeekDayDisplayType(WEEKDAY_DISPLAY_TYPES[selIndex]);
                outputSampleCode("calendar.setControlsPosition(JCalendar."
                        + WEEKDAY_DISPLAY_NAMES[selIndex].toUpperCase() + ");");
            }
        });
        c.gridx++;
        controlsPanel.add(weekdayDisplayComboBox, c);

        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Selection mode:"), c);
        selectionModeComboBox = new JComboBox(SELECTION_NAMES);
        selectionModeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selIndex = ((JComboBox)e.getSource()).getSelectedIndex();
                calendar.setSelectionMode(SELECTION_TYPES[selIndex]);
                outputSampleCode("calendar.setSelectionMode(ListSelectionModel."
                        + SELECTION_NAMES[selIndex].toUpperCase().replace(' ', '_')
                        + "_SELECTION);");
            }
        });
        c.gridx++;
        controlsPanel.add(selectionModeComboBox, c);

        showHorizontalGridCheckbox = new JCheckBox("Horizontal lines");
        showHorizontalGridCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((JCheckBox)e.getSource()).isSelected();
                calendar.setShowHorizontalLines(sel);
                outputSampleCode("calendar.setShowHorizontalLines(" + sel + ");");
            }
        });
        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(showHorizontalGridCheckbox, c);

        showVerticalGridCheckbox = new JCheckBox("Vertical lines");
        showVerticalGridCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((JCheckBox)e.getSource()).isSelected();
                calendar.setShowVerticalLines(sel);
                outputSampleCode("calendar.setShowVerticalLines(" + sel + ");");
            }
        });
        c.gridx++;
        controlsPanel.add(showVerticalGridCheckbox, c);


        firstDayOfWeekCheckbox = new JCheckBox("Change first day of week allowed");
        firstDayOfWeekCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((JCheckBox)e.getSource()).isSelected();
                calendar.setChangingFirstDayOfWeekAllowed(sel);
                outputSampleCode("calendar.setChangingFirstDayOfWeekAllowed(" + sel + ");");
            }
        });
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        controlsPanel.add(firstDayOfWeekCheckbox, c);
        c.gridwidth = 1;


        showTooltipCheckBox = new JCheckBox("Show cell tooltip");
        showTooltipCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((JCheckBox)e.getSource()).isSelected();
                tooltipPatternsComboBox.setEnabled(sel);
                calendar.setShowCellTooltips(sel);
                outputSampleCode("calendar.setShowCellTooltips(" + sel + ");");
            }
        });
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        controlsPanel.add(showTooltipCheckBox, c);
        c.gridwidth = 1;


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Tooltip patterns:"), c);
        tooltipPatternsComboBox = new JComboBox(TOOLTIP_PATTERNS);
        tooltipPatternsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pattern = (String)((JComboBox)e.getSource()).getSelectedItem();
                calendar.setTooltipDatePattern(pattern);
                outputSampleCode("calendar.setTooltipDatePattern(\"" + pattern + "\");");
            }
        });
        c.gridx++;
        controlsPanel.add(tooltipPatternsComboBox, c);


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Colors:"), c);
        colorsComboBox = new JComboBox(JCalendar.COLOR_PROPERTIES);
        colorsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColorButton();
            }
        });
        c.gridx++;
        controlsPanel.add(colorsComboBox, c);

        colorChooser = new JColorChooser();
        colorChooser.addChooserPanel(new LnFColorsChooserPanel());
        colorChooser.addChooserPanel(new HTMLColorsChooserPanel());
        colorChooserDialog = JColorChooser.createDialog(this, "Choose a color", true,
                colorChooser, new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        Color color = colorChooser.getColor();
                        calendar.setColor((String)colorsComboBox.getSelectedItem(), color);
                        colorButton.setBackground(color);
                        outputSampleCode("calendar.setColor(\""
                                + colorsComboBox.getSelectedItem() + "\", new Color("
                                + color.getRed() + ", " + color.getGreen() + ", "
                                + color.getBlue() + "));");
                    }
                }, null);
        lnfComboBox.addRootComponent(colorChooser);
        lnfComboBox.addRootComponent(colorChooserDialog);

        colorButton = new JButton("...");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorChooser.setColor(calendar
                        .getColor((String)colorsComboBox.getSelectedItem()));
                colorChooserDialog.setTitle("Choose a color for "
                        + colorsComboBox.getSelectedItem());
                colorChooserDialog.setVisible(true);
            }
        });
        c.gridx++;
        controlsPanel.add(colorButton, c);


        c.gridx = 0;
        c.gridy++;
        controlsPanel.add(new JLabel("Fonts:"), c);
        fontsComboBox = new JComboBox(JCalendar.FONT_PROPERTIES);
        fontsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFontButton();
            }
        });
        c.gridx++;
        controlsPanel.add(fontsComboBox, c);

        fontChooser = new JFontChooser();
        fontChooserDialog = JFontChooser.createDialog(this, "Choose a font", true,
                fontChooser, new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        Font font = fontChooser.getFont();
                        calendar.setFont((String)fontsComboBox.getSelectedItem(), font);
                        fontButton.setFont(font.deriveFont(14f));
                        fontButton.setText(font.getName() + font.getSize());
                        outputSampleCode("calendar.setFont(\""
                                + fontsComboBox.getSelectedItem() + "\", new Font(\""
                                + font.getName() + "\", Font.PLAIN"
                                + (font.isItalic() ? " | Font.ITALIC" : "")
                                + (font.isBold() ? " | Font.BOLD" : "")
                                + ", "
                                + font.getSize() + "));");
                    }
                }, null);
        lnfComboBox.addRootComponent(fontChooser);
        lnfComboBox.addRootComponent(fontChooserDialog);

        fontButton = new JButton("...");
        fontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = calendar.getFont((String)fontsComboBox.getSelectedItem());
                if (font == null) {
                    font = fontButton.getFont();
                }
                fontChooser.setFont(font);
                fontChooserDialog.setTitle("Choose a font for "
                        + fontsComboBox.getSelectedItem());
                fontChooserDialog.setVisible(true);
            }
        });
        c.gridx++;
        controlsPanel.add(fontButton, c);


        JPanel sampleCodePanel = new JPanel();
        sampleCodePanel.setBorder(BorderFactory.createTitledBorder("Sample code"));
        sampleCodePanel.setLayout(new GridBagLayout());

        sampleCodeTextArea = new JTextArea("JCalendar calendar = new JCalendar();\n");
        sampleCodeTextArea.setRows(8);
        sampleCodeTextArea.setEditable(false);
        sampleCodePanel.add(new JScrollPane(sampleCodeTextArea),
                new GridBagConstraints(0, 0, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        JButton sampleCodeCopyButton = new JButton("Copy to Clipboard");
        sampleCodeCopyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                sampleCodeTextArea.copy();
            }
        });
        sampleCodePanel.add(sampleCodeCopyButton, new GridBagConstraints(0, 1, 1, 1, 1, 0,
            GridBagConstraints.EAST, GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));

        JButton sampleCodeClearButton = new JButton("Reset");
        sampleCodeClearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                calendar.resetToDefaults();
                updateConfiguration();
                clearSampleCode();
            }
        });
        sampleCodePanel.add(sampleCodeClearButton, new GridBagConstraints(1, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));

        add(sampleCodePanel, new GridBagConstraints(0, 1, 2, 1, 1, 3,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,  new Insets(0, 0, 0, 0), 0, 0));



        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.setLayout(new GridBagLayout());
        add(outputPanel, new GridBagConstraints(0, 2, 2, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,  new Insets(0, 0, 0, 0), 0, 0));

        outputTextArea = new JTextArea("");
        outputTextArea.setRows(5);
        outputTextArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputTextArea),
                new GridBagConstraints(0, 0, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        JButton selectedDateButton = new JButton("Selected Date");
        selectedDateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                printSelectedDates(false);
            }
        });
        outputPanel.add(selectedDateButton, new GridBagConstraints(0, 1, 1, 1, 1, 0,
            GridBagConstraints.EAST, GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));

        JButton selectedDatesButton = new JButton("Selected Dates");
        selectedDatesButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                printSelectedDates(true);
            }
        });
        outputPanel.add(selectedDatesButton, new GridBagConstraints(1, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.NONE,  new Insets(0, 0, 0, 0), 0, 0));



        updateConfiguration();
        clearSampleCode();
    }

    private void updateConfiguration() {
        controlsShowCheckbox.setSelected(calendar.isShowControls());
        controlsTopRadioButton.setSelected(calendar.getControlsPosition() == JCalendar.TOP);
        controlsBottomRadioButton.setSelected(calendar.getControlsPosition() == JCalendar.BOTTOM);
        updateYearsOnChangeCheckBox.setSelected(calendar.isUpdateYearsOnChange());
        minYearAmountSpinner.setValue(calendar.getMinYearAmount());
        maxYearAmountSpinner.setValue(calendar.getMaxYearAmount());
        int weekDayDisplayType = calendar.getWeekDayDisplayType();
        if ( weekDayDisplayType == JCalendar.ONE) {
            weekdayDisplayComboBox.setSelectedIndex(0);
        } else if (weekDayDisplayType == JCalendar.SHORT) {
            weekdayDisplayComboBox.setSelectedIndex(1);
        } else {
            weekdayDisplayComboBox.setSelectedIndex(2);
        }
        int selectionMode = calendar.getSelectionMode();
        if ( selectionMode == ListSelectionModel.SINGLE_SELECTION) {
            selectionModeComboBox.setSelectedIndex(0);
        } else if (selectionMode == ListSelectionModel.SINGLE_INTERVAL_SELECTION) {
            selectionModeComboBox.setSelectedIndex(1);
        } else {
            selectionModeComboBox.setSelectedIndex(2);
        }
        showHorizontalGridCheckbox.setSelected(calendar.getShowHorizontalLines());
        showVerticalGridCheckbox.setSelected(calendar.getShowVerticalLines());
        firstDayOfWeekCheckbox.setSelected(calendar.isChangingFirstDayOfWeekAllowed());
        showTooltipCheckBox.setSelected(calendar.isShowCellTooltips());
        String pattern = (calendar.getTooltipDateFormat() instanceof SimpleDateFormat ?
            ((SimpleDateFormat)calendar.getTooltipDateFormat()).toPattern() : null);
        if (pattern != null) {
            tooltipPatternsComboBox.setSelectedItem(pattern);
        }
        updateColorButton();
        updateFontButton();
    }

    private void updateColorButton() {
        String property = (String)colorsComboBox.getSelectedItem();
        if (property != null) {
            colorButton.setBackground(calendar.getColor(property));
        }
    }

    private void updateFontButton() {
        String property = (String)fontsComboBox.getSelectedItem();
        if (property != null) {
            Font font = calendar.getFont(property);
            if (font != null) {
                fontButton.setFont(font.deriveFont(14f));
                fontButton.setText(font.getName() + ", " + font.getSize());
            }
            else {
                fontButton.setFont(null);
                fontButton.setText("...");
            }
        }
    }

    private void clearSampleCode() {
        sampleCodeTextArea.setText("JCalendar calendar = new JCalendar();\n");
    }

    private void outputSampleCode(String code) {
        sampleCodeTextArea.setText( sampleCodeTextArea.getText()
            + code + (code.endsWith("\n") ? "" : "\n"));
    }

    private void printSelectedDates(boolean all) {
        Date[] dates = all ? calendar.getSelectedDates()
                : new Date[]{calendar.getSelectedDate()};
        StringBuffer x = new StringBuffer();
        for (int i = 0; i < dates.length; i++) {
            if (dates[i] != null) {
                x.append(dateFormat.format(dates[i])).append("\n");
            }
        }
        if (x.length() <= 0) {
            x.append("No dates selected.");
        }
        outputTextArea.setText(x.toString());
    }

    private class Spinner extends JPanel {

        private JTextField spinnerTextField;
        private ChangeListener listener;

        public Spinner(ChangeListener listener) {
            this.listener = listener;

            setLayout(new GridBagLayout());

            spinnerTextField = new JTextField("", 3) {
                protected Document createDefaultModel() {
                    return new NumberDocument();
                }
            };
            spinnerTextField.setHorizontalAlignment(JTextField.RIGHT);
            spinnerTextField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    Spinner.this.listener.stateChanged(new ChangeEvent(e));
                }

                public void insertUpdate(DocumentEvent e) {
                    changedUpdate(e);
                }

                public void removeUpdate(DocumentEvent e) {
                    //changedUpdate(e);
                }
            });

            add(spinnerTextField, new GridBagConstraints(0, 0, 1, 2, 1, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

            ArrowButton spinnerIncButton = new ArrowButton(SwingConstants.NORTH, 1, 3);
            spinnerIncButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    spinnerTextField.setText(String.valueOf(
                            Integer.parseInt(spinnerTextField.getText()) + 1));
                }
            });
            ArrowButton spinnerDecButton = new ArrowButton(SwingConstants.SOUTH, 1, 3);
            spinnerDecButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    spinnerTextField.setText(String.valueOf(
                            Integer.parseInt(spinnerTextField.getText()) - 1));
                }
            });

            add(spinnerIncButton, new GridBagConstraints(1, 0, 1, 1,
                    0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(spinnerDecButton, new GridBagConstraints(1, 1, 1, 1,
                    0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
        }

        public int getValue() {
            int x = 0;
            try {
                x = Integer.parseInt(spinnerTextField.getText());
            } catch(NumberFormatException exc) {}
            return x;
        }

        public void setValue(int value) {
            spinnerTextField.setText(String.valueOf(value));
        }
    }

    public static void main(String[] args) {
        // getiing the major and minor version of the current JRE
        String verStr = System.getProperty("java.version");
        int majVerIndex = verStr.indexOf('.');
        int majVer;
        try {
            majVer = Integer.parseInt(verStr.substring(0, majVerIndex));
        } catch (NumberFormatException exc) {
            majVer = 1;
        }
        int minVerIndex = verStr.indexOf('.', majVerIndex + 1);
        int minVer;
        try {
            minVer = Integer.parseInt(verStr.substring(majVerIndex + 1, minVerIndex));
        } catch (NumberFormatException exc) {
            minVer = 3;
        }

        UIManager.installLookAndFeel("Kunstoff",
                "com.incors.plaf.kunststoff.KunststoffLookAndFeel");
        UIManager.installLookAndFeel("Metouia",
                "net.sourceforge.mlf.metouia.MetouiaLookAndFeel");

        // some lnf works only in 1.4
        if (majVer > 1 || (majVer == 1 && minVer >= 4)) {
            UIManager.installLookAndFeel("JGoodies Plastic3D",
                    "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
            UIManager.installLookAndFeel("JGoodies Plastic",
                    "com.jgoodies.looks.plastic.PlasticLookAndFeel");
            UIManager.installLookAndFeel("JGoodies Plastic XP",
                    "com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
//            if (System.getProperty("os.name").indexOf("Windows") >= 0) {
//                UIManager.installLookAndFeel("JGoodies Windows",
//                        "com.jgoodies.looks.windows.WindowsLookAndFeel");
//            }
        }

        JFrame f = new JFrame("JCalendar Demo");
        JCalendarDemo x = new JCalendarDemo();
        f.getContentPane().add(x);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println();
                System.exit(0);
            }
        });
        f.pack();
        f.setVisible(true);
        // Setting the position of the dialog on the center of the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation((int)d.getWidth()/2 - (int)f.getPreferredSize().getWidth()/2,
                (int)d.getHeight()/2 - (int)f.getPreferredSize().getHeight()/2);
    }
}
