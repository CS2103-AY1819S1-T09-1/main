package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * A UI component for displaying a {@code Task} in a calendar cell.
 */
public class CalendarTaskCard extends UiPart<Region> {
    private static final String FXML = "CalendarTaskCard.fxml";

    public final Task task;

    @FXML
    private Label name;
    @FXML
    private Label id;

    public CalendarTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarTaskCard)) {
            return false;
        }

        // state check
        CalendarTaskCard card = (CalendarTaskCard) other;
        return id.getText().equals(card.id.getText()) && task.equals(card.task);
    }
}
