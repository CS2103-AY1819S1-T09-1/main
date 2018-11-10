package guitests.guihandles;

import static seedu.address.ui.CalendarPanel.COLS;
import static seedu.address.ui.CalendarPanel.ROWS;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Provides a handle for {@code CalendarPanelHandle} containing the grid of
 * {@code CalendarContentCell}.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {
    public static final String CALENDAR_PANEL_ID = "#calendarPanel";

    private static final String GRID_PANE_ID = "#calendarGridPane";

    private final GridPane calendarGridPane;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);
        calendarGridPane = getChildNode(GRID_PANE_ID);
    }

    /**
     * Returns the calendar content cell handle for the specified row and column
     * (excluding header row).
     *
     * @throws IllegalArgumentException if the index is out of bounds.
     *
     * @throws IllegalStateException    if the selected card is currently not in the
     *                                  scene graph.
     */
    public CalendarContentCellHandle getCellHandle(int row, int col) {
        if (col < 0 || col >= COLS || row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        return getAllContentCells().stream()
                .filter(cell -> GridPane.getRowIndex(cell) == row && GridPane.getColumnIndex(cell) == col)
                .map(CalendarContentCellHandle::new).findFirst().orElseThrow(IllegalStateException::new);

    }

    /**
     * Returns all calendar content cell nodes in the scene graph.
     */
    private Set<Node> getAllContentCells() {
        return guiRobot.from(calendarGridPane).lookup(CalendarContentCellHandle.CALENDAR_CONTENT_CELL_ID).queryAll();
    }
}
