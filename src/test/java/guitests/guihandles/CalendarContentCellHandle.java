package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import javafx.scene.text.Text;

import seedu.address.model.task.Task;

/**
 * Provides a handle for {@code CalendarContentCell} containing the list of
 * {@code CalendarTaskCard}.
 */
public class CalendarContentCellHandle extends NodeHandle<Node> {
    public static final String CALENDAR_CONTENT_CELL_ID = "#calendarContentCell";

    private static final String CELL_HEADER_ID = "#calendarContentCellHeader";
    private static final String TASK_LIST_VIEW_ID = "#calendarContentCellListView";

    private final Text cellHeader;
    private final ListView<Task> taskListView;

    public CalendarContentCellHandle(Node calendarContentCellNode) {
        super(calendarContentCellNode);
        cellHeader = getChildNode(CELL_HEADER_ID);
        taskListView = getChildNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Navigates the listview to display {@code task}.
     */
    public void navigateToCard(Task task) {
        if (!taskListView.getItems().contains(task)) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        guiRobot.interact(() -> {
            taskListView.scrollTo(task);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= taskListView.getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            taskListView.scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the calendar task card handle of the task associated with the {@code index} in
     * the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the
     *                               scene graph.
     */
    public CalendarTaskCardHandle getCalendarTaskCardHandle(int index) {
        return getChildCardNodes().stream().map(CalendarTaskCardHandle::new)
                .filter(handle -> handle.equals(getTask(index))).findFirst().orElseThrow(IllegalStateException::new);
    }

    private Task getTask(int index) {
        return taskListView.getItems().get(index);
    }

    /**
     * Returns all child card nodes in the scene graph. Card nodes that are visible in the
     * listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getChildCardNodes() {
        return guiRobot.from(taskListView).lookup(CalendarTaskCardHandle.CALENDAR_TASK_CARD_ID).queryAll();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return taskListView.getItems().size();
    }
}
