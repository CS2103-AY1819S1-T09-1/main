package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarPanelClickEvent;
import seedu.address.model.task.Task;

/**
 * Panel for displaying the calendar.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private static final String CALENDAR_PANEL_LIST_VIEW_CLASS = "calendar-panel-list-view";
    private static final int COLS = 7; // 7 Days in a week
    private static final int ROWS = 7; // 6 Rows + header
    private static final int HEADER_ROW = 0;
    private static final int ROW_HEIGHT = 100;
    private static final int COL_WIDTH = 130;
    private static final String[] COL_HEADERS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday" };

    private static final Font COL_HEADER_FONT = new Font("Segoe UI", 14);
    private static final Font CELL_HEADER_FONT = new Font("Segoe UI", 12);

    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final ObservableList<Task> taskList;
    private final ObservableValue<Calendar> calendar;
    private final VBox[][] gridCells;

    @FXML
    private Text calendarHeader;

    @FXML
    private GridPane calendarGridPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        super(FXML);
        this.taskList = taskList;
        this.calendar = calendar;
        this.gridCells = new VBox[ROWS][COLS];
        buildGridPane();

        this.calendar.addListener((cal, oldCal, newCal) -> {
            this.updateCalendar(newCal);
        });
        registerAsAnEventHandler(this);
    }

    /**
     * Builds the calendar grid.
     */
    private void buildGridPane() {
        buildGrid();
        writeBox();
        setGridCells();
        writeHeaders();
        writeMonthHeader(calendar.getValue());
        // Do this once to write initial values
        updateCalendar(calendar.getValue());
    }

    /**
     * Fills in the contents of each grid non-header cell by calling buildCell on
     * it.
     */
    private void updateCalendar(Calendar calendar) {
        writeMonthHeader(calendar);
        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                updateCell(i, j, calendar);
            }
        }
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
     * Finds the correct month and day that a calendar cell should represent based
     * on its position in the grid.
     */
    private Pair<Calendar, Integer> getCellCalendarAndDate(int row, int col, Calendar curMonth) {
        Calendar filterCalendar;
        int displayDate;

        // Set to first day of month so that we can find out the weekday it falls on
        curMonth.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfMonth = curMonth.get(Calendar.DAY_OF_WEEK);
        int dateInCurMonth = (row - 1) * 7 + col - firstDayOfMonth + 2;
        if (dateInCurMonth < 1) {
            // This grid belongs to previous month
            Calendar prevMonth = (Calendar) curMonth.clone();
            prevMonth.add(Calendar.MONTH, -1);
            filterCalendar = prevMonth;
            displayDate = dateInCurMonth + prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (dateInCurMonth > curMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            // This grid belongs to the next month
            Calendar nextMonth = (Calendar) curMonth.clone();
            nextMonth.add(Calendar.MONTH, 11);
            filterCalendar = nextMonth;
            displayDate = dateInCurMonth - curMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else {
            filterCalendar = curMonth;
            displayDate = dateInCurMonth;
        }

        return new Pair<>(filterCalendar, displayDate);
    }

    /**
     * Updates a cell by clearing its old contents and writing new ones.
     */
    private void updateCell(int row, int col, Calendar curMonth) {
        VBox cell = gridCells[row][col];
        cell.getChildren().clear();

        VBox newContents = buildCell(row, col, curMonth);
        cell.getChildren().add(newContents);
    }

    /**
     * Builds the content for an individual cell based on its index.
     */
    private VBox buildCell(int row, int col, Calendar curMonth) {
        Pair<Calendar, Integer> cellCalendarAndDate = getCellCalendarAndDate(row, col, curMonth);
        Calendar filterCalendar = cellCalendarAndDate.getKey();
        int displayDate = cellCalendarAndDate.getValue();

        Text cellHeader = new Text(Integer.toString(displayDate));
        if (curMonth.equals(filterCalendar)) {
            cellHeader.setFill(Color.WHITE);
        } else {
            cellHeader.setFill(Color.LIGHTGRAY);
        }
        cellHeader.setFont(CELL_HEADER_FONT);

        ListView<Task> cellListView = new ListView<>();
        FilteredList<Task> gridTaskList = this.taskList
                .filtered((Task t) -> isTaskBelongToDate(t, filterCalendar, displayDate));

        cellListView.setItems(gridTaskList);
        cellListView.getStyleClass().add(CALENDAR_PANEL_LIST_VIEW_CLASS);
        cellListView.setCellFactory(listView -> new CalendarTaskListCell());

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_RIGHT);
        box.getChildren().add(cellHeader);
        box.getChildren().add(cellListView);

        return box;
    }

    /**
     * Returns a boolean representing whether the task falls on the date specified.
     */
    private boolean isTaskBelongToDate(Task task, Calendar filterCalendar, int date) {
        Calendar taskCalendar = task.getEndDateTime().getCalendar();
        return taskCalendar.get(Calendar.YEAR) == filterCalendar.get(Calendar.YEAR)
                && taskCalendar.get(Calendar.MONTH) == filterCalendar.get(Calendar.MONTH)
                && taskCalendar.get(Calendar.DAY_OF_MONTH) == date;
    }

    /**
     * Writes day headers to calendar grid.
     */
    private void writeHeaders() {
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#515658"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        for (int i = 0; i < COLS; i++) {
            Node node = gridCells[HEADER_ROW][i];
            VBox box = (VBox) node;
            Text header = new Text(COL_HEADERS[i]);
            header.setFill(Color.WHITE);
            header.setFont(COL_HEADER_FONT);
            box.setAlignment(Pos.CENTER);
            box.setBackground(background);

            box.getChildren().add(header);
        }
    }

    /**
     * Sets the 2d array of {@code VBox} objects for random access by index.
     */
    private void setGridCells() {
        for (Node node : calendarGridPane.getChildren()) {
            int gridRow = GridPane.getRowIndex(node);
            int gridCol = GridPane.getColumnIndex(node);

            this.gridCells[gridRow][gridCol] = (VBox) node;
        }
    }

    /**
     * Write grid boxes.
     */
    private void writeBox() {
        Border border = new Border(new BorderStroke(Paint.valueOf("#4d4d4d"), BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderStroke.THIN));

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                VBox box = new VBox();
                box.setBorder(border);
                box.setAlignment(Pos.TOP_RIGHT);
                box.setPadding(new Insets(2, 2, 2, 2));
                calendarGridPane.add(box, i, j);
            }
        }
    }

    /**
     * Writes grid with row/col dimension constraints.
     */
    private void buildGrid() {
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

    /**
     *
     * TODO: Write new cell for calendar Custom {@code ListCell} that displays the
     * graphics of a {@code Person} using a {@code PersonCard}.
     */
    class CalendarTaskListCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            // Fire event to update task detatil panel
            setOnMouseClicked((event) -> {
                if (task != null) {
                    logger.fine("Clicked on task in calendar panel : '" + task + "'");
                    raise(new CalendarPanelClickEvent(task));
                }
            });

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CalendarTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
