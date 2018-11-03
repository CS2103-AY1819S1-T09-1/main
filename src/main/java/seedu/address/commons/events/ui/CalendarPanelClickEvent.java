package seedu.address.commons.events.ui;

import seedu.address.model.task.Task;

/**
 * Represents a click event in the Calendar Panel
 */
public class CalendarPanelClickEvent extends TaskPanelSelectionChangedEvent {

    public CalendarPanelClickEvent(Task newSelection) {
        super(newSelection);
    }
}
