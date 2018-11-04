package seedu.address.ui;

import java.util.Calendar;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarPanelClickEvent;
import seedu.address.model.task.Task;

/**
 * Calendar cell that displays the tasks falling on said day.
 */
public class CalendarContentCell extends UiPart<Region> {
    private static final String FXML = "CalendarContentCell.fxml";

    private final Logger logger = LogsCenter.getLogger(CalendarContentCell.class);

    private final int row;
    private final int col;
    private final FilteredList<Task> filteredTaskList;

    @FXML
    private Text calendarContentCellHeader;

    @FXML
    private ListView<Task> calendarContentCellListView;

    public CalendarContentCell(int row, int col, ObservableList<Task> taskList, ObservableValue<Calendar> calendar) {
        super(FXML);
        this.row = row;
        this.col = col;
        this.filteredTaskList = new FilteredList<>(taskList);

        setConnections(calendar);

        // Call {@code setContents} once initially
        setContents(calendar.getValue());
        registerAsAnEventHandler(this);
    }

    /**
     * Initialises {@code ListView} for tasks and subscribe to calendar updates.
     */
    private void setConnections(ObservableValue<Calendar> calendar) {
        calendarContentCellListView.setItems(filteredTaskList);
        calendarContentCellListView.setCellFactory(listView -> new CalendarTaskListCell());

        calendar.addListener((cal, oldCal, newCal) -> {
            this.setContents(newCal);
        });
    }

    /**
     * Finds the correct month and day that a calendar cell should represent based
     * on its position in the grid.
     */
    private Pair<Calendar, Integer> getCellCalendarAndDate(Calendar curMonth) {
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
     * Sets the cell content based on the current month.
     */
    private void setContents(Calendar curMonth) {
        Pair<Calendar, Integer> cellCalendarAndDate = getCellCalendarAndDate(curMonth);
        Calendar filterCalendar = cellCalendarAndDate.getKey();
        int displayDate = cellCalendarAndDate.getValue();

        calendarContentCellHeader.setText(Integer.toString(displayDate));
        if (curMonth.equals(filterCalendar)) {
            calendarContentCellHeader.setFill(Color.WHITE);
        } else {
            calendarContentCellHeader.setFill(Color.LIGHTGRAY);
        }

        filteredTaskList.setPredicate((Task t) -> isTaskBelongToDate(t, filterCalendar, displayDate));
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
     *
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a
     * {@code CalendarTaskCard}.
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
