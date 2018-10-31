package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a deselection in the Task List Panel
 */
public class TaskPanelDeselectionEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
