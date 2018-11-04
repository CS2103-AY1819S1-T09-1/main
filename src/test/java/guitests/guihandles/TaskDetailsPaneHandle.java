package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * Provides a handle to a task details pane.
 */
public class TaskDetailsPaneHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String START_DATE_TIME_FIELD_ID = "#startDateTime";
    private static final String END_DATE_TIME_FIELD_ID = "#endDateTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label startDateTimeLabel;
    private final Label endDateTimeLabel;
    private final List<Label> tagLabels;

    public TaskDetailsPaneHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        startDateTimeLabel = getChildNode(START_DATE_TIME_FIELD_ID);
        endDateTimeLabel = getChildNode(END_DATE_TIME_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getStartDateTime() {
        return startDateTimeLabel.getText();
    }

    public String getEndDateTime() {
        return endDateTimeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code task}.
     */
    public boolean equals(Task task) {
        return getName().equals(task.getName().toString())
                && getStartDateTime().equals(
                        task.getStartDateTime().getDate())
                && getEndDateTime().equals(task.getEndDateTime().getDate())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(task.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
