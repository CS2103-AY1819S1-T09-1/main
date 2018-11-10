package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLAUGHTER;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import guitests.guihandles.CalendarPanelHandle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Task;
import seedu.address.testutil.CalendarUtil;
import seedu.address.testutil.TaskBuilder;

public class CalendarContentCellTest extends GuiUnitTest {
    // All tasks here end in Jan 2017
    private static final ObservableList<Task> TYPICAL_CALENDAR_TASKS = buildTypicalTasks();

    // Jan 2017 is chosen because the month starts on the Sunday, the first column
    // of the Calendar
    private static final ObservableValue<Calendar> TYPICAL_MONTH = new ReadOnlyObjectWrapper<>(
            CalendarUtil.getCalendar(2017, 1));

    private static final String DATE_STRING_5_JAN_2017 = "20170105";
    private static final String DATE_STRING_10_JAN_2017 = "20170110";
    private static final String TIME_STRING_NOON = "1200";

    private CalendarPanelHandle calendarPanelHandle;

    /**
     * Test for when first day of month is first column of calendar.
     */
    @Test
    public void displayAligned() {
        initUi(TYPICAL_CALENDAR_TASKS, TYPICAL_MONTH);

        TYPICAL_CALENDAR_TASKS.stream()
                .filter(task -> task.getEndDateTime().getCalendar().get(Calendar.DAY_OF_MONTH) == 5).forEach(task -> {
                    assertTrue(calendarPanelHandle.getCellHandle(1, 4).isContainTask(task));
                });

        TYPICAL_CALENDAR_TASKS.stream()
                .filter(task -> task.getEndDateTime().getCalendar().get(Calendar.DAY_OF_MONTH) == 10).forEach(task -> {
                    assertTrue(calendarPanelHandle.getCellHandle(2, 2).isContainTask(task));
                });
    }

    /**
     * Test for when first day of month is not first column of calendar.
     */

    @Test
    public void displayOffset() {
        String dateString2Feb2017 = "20170202";
        String dateString9Feb2017 = "20170209";

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new TaskBuilder().withName(VALID_NAME_BRUSH)
                .withEndDateTime(new DateTime(dateString2Feb2017, TIME_STRING_NOON)).build());
        tasks.add(new TaskBuilder().withName(VALID_NAME_SLAUGHTER)
                .withEndDateTime(new DateTime(dateString2Feb2017, TIME_STRING_NOON)).build());

        tasks.add(new TaskBuilder().withName(VALID_NAME_SLAUGHTER)
                .withEndDateTime(new DateTime(dateString9Feb2017, TIME_STRING_NOON)).build());

        ObservableList<Task> taskList = FXCollections.observableList(tasks);
        ObservableValue<Calendar> feb2017 = new ReadOnlyObjectWrapper<>(CalendarUtil.getCalendar(2017, 2));

        initUi(taskList, feb2017);

        taskList.stream().filter(task -> task.getEndDateTime().getCalendar().get(Calendar.DAY_OF_MONTH) == 2)
                .forEach(task -> {
                    assertTrue(calendarPanelHandle.getCellHandle(1, 4).isContainTask(task));
                });

        taskList.stream().filter(task -> task.getEndDateTime().getCalendar().get(Calendar.DAY_OF_MONTH) == 9)
                .forEach(task -> {
                    assertTrue(calendarPanelHandle.getCellHandle(2, 4).isContainTask(task));
                });
    }

    /**
     * Returns a {@code ObservableList<Task>} containing three tasks in the typical
     * month.
     *
     * Two tasks with different names fall on 5th of the typical month. One task
     * falls on 10th of the typical month.
     */
    private static ObservableList<Task> buildTypicalTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(new TaskBuilder().withName(VALID_NAME_BRUSH)
                .withEndDateTime(new DateTime(DATE_STRING_5_JAN_2017, TIME_STRING_NOON)).build());
        taskList.add(new TaskBuilder().withName(VALID_NAME_SLAUGHTER)
                .withEndDateTime(new DateTime(DATE_STRING_5_JAN_2017, TIME_STRING_NOON)).build());

        taskList.add(new TaskBuilder().withName(VALID_NAME_SLAUGHTER)
                .withEndDateTime(new DateTime(DATE_STRING_10_JAN_2017, TIME_STRING_NOON)).build());

        return FXCollections.observableList(taskList);
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel}
     * backed by {@code backingList}. Also shows the {@code Stage} that displays
     * only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Task> backingList, ObservableValue<Calendar> backingCalendar) {
        // PersonListPanel personListPanel = new PersonListPanel(backingList);
        CalendarPanel calendarPanel = new CalendarPanel(backingList, backingCalendar);
        uiPartRule.setUiPart(calendarPanel);
        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
    }
}
