package seedu.address.ui;

import java.util.Calendar;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
import javafx.util.Pair;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 * Panel for displaying the calendar.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private static final int COLS = 7; // 7 Days in a week
    private static final int ROWS = 7; // 6 Rows + header
    private static final int HEADER_ROW = 0;
    private static final int ROW_HEIGHT = 80;
    private static final int COL_WIDTH = 105;
    private static final String[] COL_HEADERS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday" };

    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final ObservableList<Task> taskList;
    private final ObservableValue<Calendar> calendar;
    private final Node[][] gridCells;

    @FXML
    private GridPane taskGridPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        super(FXML);
        this.taskList = taskList;
        this.calendar = calendar;
        this.gridCells = new Node[ROWS][COLS];
        buildGridPane();
        this.calendar.addListener((cal, oldCal, newCal) -> {
            this.fillContents(newCal);
        });
        this.taskList.addListener(
                (ListChangeListener.Change<? extends Task> change) -> this.fillContents(calendar.getValue()));
        this.fillContents(calendar.getValue());
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
    }

    /**
     * Fills in the contents of each grid non-header cell by calling buildCell on
     * it..
     */
    private void fillContents(Calendar calendar) {
        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buildCell(i, j, calendar);
            }
        }
    }

    private Pair<Calendar, Integer> getCellCalendarAndDate(int row, int col, Calendar curMonth) {
        Calendar filterCalendar;
        int displayDate;

        int firstDayOfMonth = curMonth.get(Calendar.DAY_OF_WEEK);
        int dateInCurMonth = (row - 1) * 7 + col - firstDayOfMonth + 2;
        // This grid belongs to previous month
        if (dateInCurMonth < 1) {
            Calendar prevMonth = (Calendar) curMonth.clone();
            prevMonth.add(Calendar.MONTH, -1);
            filterCalendar = prevMonth;
            displayDate = dateInCurMonth + prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (dateInCurMonth > curMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) {
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
     * Builds the content for an individual cell based on its index.
     */
    private void buildCell(int row, int col, Calendar curMonth) {
        Pair<Calendar, Integer> cellCalendarAndDate = getCellCalendarAndDate(row, col, curMonth);
        Calendar filterCalendar = cellCalendarAndDate.getKey();
        int displayDate = cellCalendarAndDate.getValue();

        VBox box = (VBox) gridCells[row][col];
        box.getChildren().clear();

        ListView<Task> gridTaskListView = new ListView<>();
        FilteredList<Task> gridTaskList = this.taskList
                .filtered((Task t) -> isBelongToCurCell(t, filterCalendar, displayDate));

        gridTaskListView.setItems(gridTaskList);
        gridTaskListView.setCellFactory(listView -> new TaskListViewCell());
        box.getChildren().add(new Text(Integer.toString(displayDate)));
        box.getChildren().add(gridTaskListView);
    }

    private boolean isBelongToCurCell(Task task, Calendar filterCalendar, int date) {
        Calendar taskCalendar = task.getEndDateTime().getCalendar();
        return taskCalendar.get(Calendar.YEAR) == filterCalendar.get(Calendar.YEAR)
                && taskCalendar.get(Calendar.MONTH) == filterCalendar.get(Calendar.MONTH)
                && taskCalendar.get(Calendar.DAY_OF_MONTH) == date;
    }

    /**
     * Writes day headers to calendar grid.
     */
    private void writeHeaders() {
        for (int i = 0; i < COLS; i++) {
            Node node = gridCells[HEADER_ROW][i];
            VBox box = (VBox) node;
            Text header = new Text(COL_HEADERS[i]);
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(header);
        }
    }

    private void setGridCells() {
        for (Node node : taskGridPane.getChildren()) {
            int gridRow = GridPane.getRowIndex(node);
            int gridCol = GridPane.getColumnIndex(node);

            this.gridCells[gridRow][gridCol] = node;
        }
    }

    /**
     * Write grid boxes.
     */
    private void writeBox() {
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        Border border = new Border(new BorderStroke(Paint.valueOf("#F0F0F0"), BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderStroke.MEDIUM));

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                VBox box = new VBox();
                box.setBackground(background);
                box.setBorder(border);
                taskGridPane.add(box, i, j);
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
            taskGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < ROWS; i++) {
            RowConstraints row;
            if (i == 0) {
                row = new RowConstraints();
            } else {
                row = new RowConstraints(ROW_HEIGHT);
            }
            taskGridPane.getRowConstraints().add(row);
        }
    }

    /**
     *
     * TODO: Write new cell for calendar Custom {@code ListCell} that displays the
     * graphics of a {@code Person} using a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
