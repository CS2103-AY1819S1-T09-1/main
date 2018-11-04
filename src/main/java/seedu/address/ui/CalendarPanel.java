package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import seedu.address.model.task.Task;

/**
 * Panel for displaying the calendar.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private static final int COLS = 7; // 7 Days in a week
    private static final int ROWS = 7; // 6 Rows + header
    private static final int HEADER_ROW = 0;
    private static final int ROW_HEIGHT = 100;
    private static final int COL_WIDTH = 130;

    @FXML
    private Text calendarHeader;

    @FXML
    private GridPane calendarGridPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        super(FXML);

        buildEmptyGrid();
        setGridContent(taskList, calendar);

        // Write it once initially. Subsequent updates will be handled by callback.
        writeMonthHeader(calendar.getValue());

        setConnections(calendar);
        registerAsAnEventHandler(this);
    }

    /**
     * Subscribe to calendar updates.
     */
    private void setConnections(ObservableValue<Calendar> calendar) {
        calendar.addListener((cal, oldCal, newCal) -> {
            writeMonthHeader(newCal);
        });
    }

    /**
     * Updates the calendar header to the month represented by the {@code Calendar}
     * object
     */
    private void writeMonthHeader(Calendar calendar) {
        calendarHeader.setText(new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)] + " "
                + Integer.toString(calendar.get(Calendar.YEAR)));
    }

    /**
     * Write contents of calendar grid.
     */
    private void setGridContent(ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (row == HEADER_ROW) {
                    calendarGridPane.add(new CalendarHeaderCell(col).getRoot(), col, row);
                } else {
                    calendarGridPane.add(new CalendarContentCell(row, col, taskList, calendar).getRoot(), col, row);
                }
            }
        }
    }

    /**
     * Writes empty grid with row/col dimension constraints.
     */
    private void buildEmptyGrid() {
        for (int i = 0; i < COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(COL_WIDTH);
            column.setHgrow(Priority.ALWAYS);
            calendarGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < ROWS; i++) {
            RowConstraints row;
            if (i == 0) {
                row = new RowConstraints();
            } else {
                row = new RowConstraints(ROW_HEIGHT);
            }
            calendarGridPane.getRowConstraints().add(row);
        }
    }
}
