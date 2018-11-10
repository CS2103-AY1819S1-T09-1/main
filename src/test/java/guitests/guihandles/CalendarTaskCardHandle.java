package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.task.Task;

/**
 * Provides a handle to a task card in a calendar content cell.
 */
public class CalendarTaskCardHandle extends NodeHandle<Node> {
    public static final String CALENDAR_TASK_CARD_ID = "#calendarTaskCard";
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";

    private final Label idLabel;
    private final Label nameLabel;

    public CalendarTaskCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code task}.
     */
    public boolean equals(Task task) {
        return getName().equals(task.getName().toString());
    }
}
