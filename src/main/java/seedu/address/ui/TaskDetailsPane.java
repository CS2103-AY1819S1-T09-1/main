package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelDeselectionEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * The Task Detail Panel of the App.
 */
public class TaskDetailsPane extends UiPart<Region> {

    private static final String FXML = "TaskDetailsPane.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;

    @FXML
    private FlowPane tags;

    public TaskDetailsPane() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded detail screen.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Task task = event.getNewSelection();
        name.setText(task.getName().toString());
        startDateTime.setText("Starting from: " + task.getStartDateTime().getDate());
        endDateTime.setText("Due by: " + task.getEndDateTime().toString());
        // Clear existing list of tags before populating with new tags
        tags.getChildren().clear();
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handleTaskPanelDeselectionEvent(TaskPanelDeselectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        // Clear details pane
        name.setText("");
        startDateTime.setText("");
        endDateTime.setText("");
        tags.getChildren().clear();
    }
}
