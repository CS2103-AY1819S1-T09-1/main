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
    private static final int ROWS = 6; // 5 Rows + header
    private static final int ROW_HEIGHT = 80;
    private static final int COL_WIDTH = 105;
    private static final String[] HEADERS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday" };

    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final ObservableList<Task> taskList;
    private final ObservableValue<Calendar> calendar;

    @FXML
    private GridPane taskGridPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        super(FXML);
        this.taskList = taskList;
        this.calendar = calendar;
        buildGridPane();
        this.calendar.addListener((cal, oldCal, newCal) -> this.fillContents());
        this.taskList.addListener((ListChangeListener.Change<? extends Task> change) -> this.fillContents());
        registerAsAnEventHandler(this);
    }

    /**
     * Builds the calendar grid.
     */
    private void buildGridPane() {
        buildGrid();
        writeBox();
        writeHeaders();
    }

    /**
     * Fills in the contents of each grid non-header cell by calling buildCell on
     * it..
     */
    private void fillContents() {
        for (int i = 1; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buildCell(i, j);
            }
        }
    }

    /**
     * Builds the content for an individual cell based on its index.
     */
    private void buildCell(int row, int col) {
        int firstDayOfMonth = calendar.getValue().get(Calendar.DAY_OF_WEEK);
        Node node = getCellNode(row, col);
        VBox box = (VBox) node;
        box.getChildren().clear();

        if (row == 1 && col + 1 < firstDayOfMonth) {
            return;
        }

        int curDate = (row - 1) * 7 + col - firstDayOfMonth + 2;

        ListView<Task> gridTaskListView = new ListView<>();
        FilteredList<Task> gridTaskList = this.taskList
                .filtered((Task t) -> t.getStartDateTime().calendar.get(Calendar.DATE) == curDate);

        gridTaskListView.setItems(gridTaskList);
        gridTaskListView.setCellFactory(listView -> new TaskListViewCell());
        box.getChildren().add(gridTaskListView);
    }

    /**
     * Writes day headers to calendar grid.
     */
    private void writeHeaders() {
        for (int i = 0; i < COLS; i++) {
            Node node = getCellNode(0, i);
            VBox box = (VBox) node;
            Text header = new Text(HEADERS[i]);
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(header);
        }
    }

    private Node getCellNode(int row, int col) {
        for (Node node : taskGridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }

        // TODO: un-uglify
        return null;
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
